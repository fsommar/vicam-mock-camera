package com.dreamteam.test.vicam;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.get;

/**
 * Created by fsommar on 2014-04-21.
 */
public class CameraServer {


        public static void main(String[] args) {
            Map<String, String> responses = new HashMap<>();
            responses.put("APC", "aPC45006300");
            responses.put("GZ", "gz646");
            responses.put("GF", "gfabc");

            get(new Route("/cgi-bin/aw_ptz") {
                @Override
                public Object handle(Request request, Response response) {
                    Optional<String> opt = Optional.of(request.queryMap("cmd").value());
                    opt = opt.filter(s -> s.startsWith("#")).map(s -> s.substring(1));
                    if (opt.isPresent()) {
                        String res = opt.get();
                        switch (res.length()) {
                            case 2:
                            case 3:
                                return responses.getOrDefault(res, "error!");
                            default:
                                return res;
                        }
                    }
                    return "error!";
                }
            });

            get(new Route("/cgi-bin/aw_cam") {
                @Override
                public Object handle(Request request, Response response) {
                    return "Hello cam!";
                }
            });

        }
}
