# Servlet Session + Span Example

* Servletのサンプルコードは https://qiita.com/niwasawa/items/b020f91ef67fc14bf86f をベースにしています。
* 参考: https://opentelemetry.io/docs/instrumentation/java/manual/

## 動かし方

1. [OpenTelemetry Collectorをインストール](https://docs.splunk.com/Observability/gdi/opentelemetry/install-the-collector.html)してください。

2. 次のコマンドで、8080ポートでJettyが起動します: 
```shellscript
make jetty-rum
```

3. http://localhost:8080/my-servlet を(Jettyがローカルホストではない場合は適切なIPアドレス等に変えて)ブラウザーで開いて、なんどかリロードして、トレースの様子を見てみましょう。
