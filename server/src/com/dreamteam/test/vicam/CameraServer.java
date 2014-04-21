package com.dreamteam.test.vicam;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
                        int len = res.length();
                        if ((len == 2 || len == 3) && responses.containsKey(res)) {
                            return responses.get(res);
                        }
                        Stream<String> keywords = Stream.of("PTS", "Z", "F");
                        if (keywords.anyMatch(res::startsWith)) {
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
