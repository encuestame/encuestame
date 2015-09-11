/*! offline 0.4.5 */
(function() {
    var a, b, c, d, e, f, g;
    d = function(a, b) {
        var c, d, e, f;
        f = [];
        for (d in b.prototype) {
            try {
                e = b.prototype[d];
                if (a[d] == null && typeof e !== "function") {
                    f.push(a[d] = e);
                } else {
                    f.push(void 0);
                }
            } catch (g) {
                c = g;
            }
        }
        return f;
    };
    a = {};
    if (a.options == null) {
        a.options = {};
    }
    c = {
        checks: {
            xhr: {
                url: function() {
                    return "/offline-test-request/" + Math.floor(Math.random() * 1e9);
                }
            },
            image: {
                url: function() {
                    return config.contextPath + "/resources/images/are-we-online.gif";
                }
            },
            active: "image"
        },
        checkOnLoad: false,
        interceptRequests: true
    };
    e = function(a, b) {
        var c, d, e, f, g, h;
        c = a;
        h = b.split(".");
        for (d = f = 0, g = h.length; f < g; d = ++f) {
            e = h[d];
            c = c[e];
            if (typeof c !== "object") {
                break;
            }
        }
        return c;
    };
    a.getOption = function(b) {
        var d, f;
        d = (f = e(a.options, b)) != null ? f : e(c, b);
        if (typeof d === "function") {
            return d();
        } else {
            return d;
        }
    };
    if (typeof window.addEventListener === "function") {
        window.addEventListener("online", function() {
            return setTimeout(a.confirmUp, 100);
        }, false);
    }
    if (typeof window.addEventListener === "function") {
        window.addEventListener("offline", function() {
            return a.confirmDown();
        }, false);
    }
    a.state = "up";
    a.markUp = function() {
        a.trigger("confirmed-up");
        if (a.state === "up") {
            return;
        }
        a.state = "up";
        return a.trigger("up");
    };
    a.markDown = function() {
        a.trigger("confirmed-down");
        if (a.state === "down") {
            return;
        }
        a.state = "down";
        return a.trigger("down");
    };
    f = {};
    a.on = function(b, c, d) {
        var e, g, h, i, j;
        g = b.split(" ");
        if (g.length > 1) {
            j = [];
            for (h = 0, i = g.length; h < i; h++) {
                e = g[h];
                j.push(a.on(e, c, d));
            }
            return j;
        } else {
            if (f[b] == null) {
                f[b] = [];
            }
            return f[b].push([ d, c ]);
        }
    };
    a.off = function(a, b) {
        var c, d, e, g, h;
        if (f[a] == null) {
            return;
        }
        if (!b) {
            return f[a] = [];
        } else {
            d = 0;
            h = [];
            while (d < f[a].length) {
                g = f[a][d], c = g[0], e = g[1];
                if (e === b) {
                    h.push(f[a].splice(d, 1));
                } else {
                    h.push(d++);
                }
            }
            return h;
        }
    };
    a.trigger = function(a) {
        var b, c, d, e, g, h, i;
        if (f[a] != null) {
            g = f[a];
            i = [];
            for (d = 0, e = g.length; d < e; d++) {
                h = g[d], b = h[0], c = h[1];
                i.push(c.call(b));
            }
            return i;
        }
    };
    b = function(a, b, c) {
        var d, e;
        d = function() {
            if (a.status && a.status < 12e3) {
                return b();
            } else {
                return c();
            }
        };
        if (a.onprogress === null) {
            a.addEventListener("error", c, false);
            a.addEventListener("timeout", c, false);
            return a.addEventListener("load", d, false);
        } else {
            e = a.onreadystatechange;
            return a.onreadystatechange = function() {
                if (a.readyState === 4) {
                    d();
                } else if (a.readyState === 0) {
                    c();
                }
                return typeof e === "function" ? e.apply(null, arguments) : void 0;
            };
        }
    };
    a.checks = {};
    a.checks.xhr = function() {
        var c, d;
        d = new XMLHttpRequest();
        d.offline = false;
        d.open("HEAD", a.getOption("checks.xhr.url"), true);
        b(d, a.markUp, a.markDown);
        try {
            d.send();
        } catch (e) {
            c = e;
            a.markDown();
        }
        return d;
    };
    a.checks.image = function() {
        var b;
        b = document.createElement("img");
        b.onerror = a.markDown;
        b.onload = a.markUp;
        b.src = a.getOption("checks.image.url");
        return void 0;
    };
    a.check = function() {
        a.trigger("checking");
        return a.checks[a.getOption("checks.active")]();
    };
    a.confirmUp = a.confirmDown = a.check;
    a.onXHR = function(a) {
        var b, c, e;
        b = function(b, c) {
            var d;
            d = b.open;
            return b.open = function(e, f, g, h, i) {
                a({
                    type: e,
                    url: f,
                    async: g,
                    flags: c,
                    user: h,
                    password: i,
                    xhr: b
                });
                return d.apply(b, arguments);
            };
        };
        e = window.XMLHttpRequest;
        window.XMLHttpRequest = function(a) {
            var c, d, f;
            c = new e(a);
            b(c, a);
            f = c.setRequestHeader;
            c.headers = {};
            c.setRequestHeader = function(a, b) {
                c.headers[a] = b;
                return f.call(c, a, b);
            };
            d = c.overrideMimeType;
            c.overrideMimeType = function(a) {
                c.mimeType = a;
                return d.call(c, a);
            };
            return c;
        };
        d(window.XMLHttpRequest, e);
        if (window.XDomainRequest != null) {
            c = window.XDomainRequest;
            window.XDomainRequest = function() {
                var a;
                a = new c();
                b(a);
                return a;
            };
            return d(window.XDomainRequest, c);
        }
    };
    g = function() {
        if (a.getOption("interceptRequests")) {
            a.onXHR(function(c) {
                var d;
                d = c.xhr;
                if (d.offline !== false) {
                    return b(d, a.confirmUp, a.confirmDown);
                }
            });
        }
        if (a.getOption("checkOnLoad")) {
            return a.check();
        }
    };
    setTimeout(g, 0);
    window.Offline = a;
}).call(this);

(function() {
    var a, b, c, d, e, f, g, h, i;
    if (!window.Offline) {
        throw new Error("Offline Reconnect brought in without offline.js");
    }
    d = {};
    f = null;
    e = function() {
        var a;
        if (d.state != null) {
            Offline.trigger("reconnect:stopped");
        }
        d.state = "inactive";
        return d.remaining = d.delay = (a = Offline.getOption("reconnect.initialDelay")) != null ? a : 3;
    };
    b = function() {
        var a, b;
        a = (b = Offline.getOption("reconnect.delay")) != null ? b : Math.min(Math.ceil(d.delay * 1.5), 3600);
        return d.remaining = d.delay = a;
    };
    g = function() {
        if (d.state === "connecting") {
            return;
        }
        d.remaining -= 1;
        Offline.trigger("reconnect:tick");
        if (d.remaining === 0) {
            return h();
        }
    };
    h = function() {
        if (d.state !== "waiting") {
            return;
        }
        Offline.trigger("reconnect:connecting");
        d.state = "connecting";
        return Offline.check();
    };
    a = function() {
        d.state = "waiting";
        Offline.trigger("reconnect:started");
        return f = setInterval(g, 1e3);
    };
    i = function() {
        clearInterval(f);
        return e();
    };
    c = function() {
        if (d.state === "connecting") {
            Offline.trigger("reconnect:failure");
            d.state = "waiting";
            return b();
        }
    };
    d.tryNow = h;
    setTimeout(function() {
        if (Offline.getOption("reconnect") !== false) {
            e();
            Offline.on("down", a);
            Offline.on("confirmed-down", c);
            Offline.on("up", i);
            return Offline.reconnect = d;
        }
    }, 0);
}).call(this);

(function() {
    var a, b, c, d, e, f;
    if (!window.Offline) {
        throw new Error("Requests module brought in without offline.js");
    }
    c = [];
    f = false;
    d = function(a) {
        Offline.trigger("requests:capture");
        if (Offline.state !== "down") {
            f = true;
        }
        return c.push(a);
    };
    e = function(a) {
        var b, c, d, e, f, g, h, i, j;
        i = a.xhr, f = a.url, e = a.type, g = a.user, d = a.password, b = a.body;
        i.abort();
        i.open(e, f, true, g, d);
        j = i.headers;
        for (c in j) {
            h = j[c];
            i.setRequestHeader(c, h);
        }
        if (i.mimeType) {
            i.overrideMimeType(i.mimeType);
        }
        return i.send(b);
    };
    a = function() {
        return c = [];
    };
    b = function() {
        var b, d, f, g, h, i;
        Offline.trigger("requests:flush");
        f = {};
        for (h = 0, i = c.length; h < i; h++) {
            d = c[h];
            g = d.url.replace(/(\?|&)_=[0-9]+/, function(a, b) {
                if (b === "?") {
                    return b;
                } else {
                    return "";
                }
            });
            f["" + d.type.toUpperCase() + " - " + g] = d;
        }
        for (b in f) {
            d = f[b];
            e(d);
        }
        return a();
    };
    setTimeout(function() {
        if (Offline.getOption("requests") !== false) {
            Offline.on("confirmed-up", function() {
                if (f) {
                    f = false;
                    return a();
                }
            });
            Offline.on("up", b);
            Offline.on("down", function() {
                return f = false;
            });
            Offline.onXHR(function(a) {
                var b, c, e, f, g;
                e = a.xhr, b = a.async;
                if (e.offline === false) {
                    return;
                }
                c = function() {
                    return d(a);
                };
                g = e.send;
                e.send = function(b) {
                    a.body = b;
                    return g.apply(e, arguments);
                };
                if (!b) {
                    return;
                }
                if (e.onprogress === null) {
                    e.addEventListener("error", c, false);
                    return e.addEventListener("timeout", c, false);
                } else {
                    f = e.onreadystatechange;
                    return e.onreadystatechange = function() {
                        if (e.readyState === 0) {
                            c();
                        } else if (e.readyState === 4 && (e.status === 0 || e.status >= 12e3)) {
                            c();
                        }
                        return typeof f === "function" ? f.apply(null, arguments) : void 0;
                    };
                }
            });
            return Offline.requests = {
                flush: b,
                clear: a
            };
        }
    }, 0);
}).call(this);

(function() {
    var a, b, c, d, e, f, g, h, i, j, k, l, m, n;
    if (!window.Offline) {
        throw new Error("Offline UI brought in without offline.js");
    }
    b = '<div class="offline-ui"><div class="offline-ui-content"></div></div>';
    a = '<a href class="offline-ui-retry"></a>';
    e = function(a) {
        var b;
        b = document.createElement("div");
        b.innerHTML = a;
        return b.children[0];
    };
    f = d = null;
    c = function(a) {
        k(a);
        return f.className += " " + a;
    };
    k = function(a) {
        return f.className = f.className.replace(new RegExp("(^| )" + a.split(" ").join("|") + "( |$)", "gi"), " ");
    };
    h = {};
    g = function(a, b) {
        c(a);
        if (h[a] != null) {
            clearTimeout(h[a]);
        }
        return h[a] = setTimeout(function() {
            k(a);
            return delete h[a];
        }, b * 1e3);
    };
    i = function(a, b) {
        var c, d, e, f, g, h;
        if (b == null) {
            b = false;
        }
        if (a === 0) {
            return "now";
        }
        c = {
            d: 86400,
            h: 3600,
            m: 60,
            s: 1
        };
        d = {
            s: "second",
            m: "minute",
            h: "hour",
            d: "day"
        };
        f = "";
        for (g in c) {
            e = c[g];
            if (a >= e) {
                h = Math.floor(a / e);
                if (b) {
                    g = " " + d[g];
                    if (h !== 1) {
                        g += "s";
                    }
                }
                return "" + h + g;
            }
        }
    };
    l = function() {
        var g, h;
        f = e(b);
        document.body.appendChild(f);
        if (Offline.reconnect != null) {
            f.appendChild(e(a));
            g = f.querySelector(".offline-ui-retry");
            h = function(a) {
                a.preventDefault();
                return Offline.reconnect.tryNow();
            };
            if (g.addEventListener != null) {
                g.addEventListener("click", h, false);
            } else {
                g.attachEvent("click", h);
            }
        }
        c("offline-ui-" + Offline.state);
        return d = f.querySelector(".offline-ui-content");
    };
    j = function() {
        l();
        Offline.on("up", function() {
            k("offline-ui-down");
            c("offline-ui-up");
            g("offline-ui-up-2s", 2);
            return g("offline-ui-up-5s", 5);
        });
        Offline.on("down", function() {
            k("offline-ui-up");
            c("offline-ui-down");
            g("offline-ui-down-2s", 2);
            return g("offline-ui-down-5s", 5);
        });
        Offline.on("reconnect:connecting", function() {
            c("offline-ui-connecting");
            return k("offline-ui-waiting");
        });
        Offline.on("reconnect:tick", function() {
            c("offline-ui-waiting");
            k("offline-ui-connecting");
            d.setAttribute("data-retry-in-seconds", Offline.reconnect.remaining);
            d.setAttribute("data-retry-in-abbr", i(Offline.reconnect.remaining));
            return d.setAttribute("data-retry-in", i(Offline.reconnect.remaining, true));
        });
        Offline.on("reconnect:stopped", function() {
            k("offline-ui-connecting offline-ui-waiting");
            d.setAttribute("data-retry-in-seconds", null);
            d.setAttribute("data-retry-in-abbr", null);
            return d.setAttribute("data-retry-in", null);
        });
        Offline.on("reconnect:failure", function() {
            g("offline-ui-reconnect-failed-2s", 2);
            return g("offline-ui-reconnect-failed-5s", 5);
        });
        return Offline.on("reconnect:success", function() {
            g("offline-ui-reconnect-succeeded-2s", 2);
            return g("offline-ui-reconnect-succeeded-5s", 5);
        });
    };
    if ((n = document.readyState) === "interactive" || n === "complete") {
        j();
    } else if (document.addEventListener != null) {
        document.addEventListener("DOMContentLoaded", j, false);
    } else {
        m = document.onreadystatechange;
        document.onreadystatechange = function() {
            if (document.readyState === "complete") {
                j();
            }
            return typeof m === "function" ? m.apply(null, arguments) : void 0;
        };
    }
}).call(this);