package main

import (
	"log"
	"net/http"

	"go.opentelemetry.io/contrib/instrumentation/net/http/otelhttp"
	"go.opentelemetry.io/otel"
	"go.opentelemetry.io/otel/attribute"
	"go.opentelemetry.io/otel/exporters/trace/jaeger"
	"go.opentelemetry.io/otel/propagation"
	"go.opentelemetry.io/otel/sdk/trace"
)

func main() {
	initTracer()
	log.Println("Service started")
	otelHandler := otelhttp.NewHandler(http.HandlerFunc(handler), "/")
	http.Handle("/", otelHandler)
	http.ListenAndServe(":8080", nil)
}

func initTracer() func() {
	tp, flush, err := jaeger.NewExportPipeline(
		jaeger.WithCollectorEndpoint("http://localhost:14268/api/traces"),
		jaeger.WithProcess(jaeger.Process{
			ServiceName: "go-service",
			Tags:        []attribute.KeyValue{},
		}),
		jaeger.WithSDK(&trace.Config{DefaultSampler: trace.AlwaysSample()}),
	)
	if err != nil {
		log.Fatal(err)
	}

	otel.SetTracerProvider(tp)
	otel.SetTextMapPropagator(propagation.NewCompositeTextMapPropagator(propagation.TraceContext{}, propagation.Baggage{}))

	return flush
}

func handler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusNoContent)
}
