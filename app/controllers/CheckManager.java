package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F;

import models.*;



public class CheckManager extends Action.Simple {

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
       
        String id = ctx.session().get("email");
        if (id != null) {
            User u = User.getLoggedIn(id);
            if ("manager".equals(u.getUserType()) || "admin".equals(u.getUserType())) {
                return delegate.call(ctx);
            } 
        }        
        return F.Promise.pure(redirect("/login"));
    }
}