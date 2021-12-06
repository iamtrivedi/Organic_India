package com.organic.india.custom_view.bottommenu;

public class Bottom_step {

    String name;
    int icon_resource;
    int route;

    public Bottom_step(String name, int icon_resource, int route) {
        this.name = name;
        this.icon_resource = icon_resource;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon_resource() {
        return icon_resource;
    }

    public void setIcon_resource(int icon_resource) {
        this.icon_resource = icon_resource;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

}
