package com.from.nowhere;

import com.from.nowhere.rest.RestVerticle;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.shell.ShellServiceOptions;
import io.vertx.ext.shell.net.TelnetOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.shell.ShellService;
import rx.Observable;


public class Mothership {

    private static Vertx vertx = null;
    public static Observable<Vertx> onAppStarted = Observable
            .defer(() -> Observable.just(vertx))
            .filter(v -> v != null);

    public static void main(String args[]) {
        VertxOptions options = new VertxOptions().setMetricsOptions(
                new DropwizardMetricsOptions().setRegistryName("test")
        );

        vertx = Vertx.vertx(options);
        vertx.deployVerticle(RestVerticle.class.getName());

        ShellService service = ShellService.create(vertx,
                new ShellServiceOptions().setTelnetOptions(
                        new TelnetOptions().
                                setHost("localhost").
                                setPort(4000)
                )
        );
        service.start();

    }
}
