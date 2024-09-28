/**
 * circles - v0.0.6 - 2015-11-27
 *
 * Copyright (c) 2015 lugolabs
 * Licensed 
 */
!function(a, b) {
    "object" == typeof exports ? module.exports = b() : "function" == typeof define && define.amd ? define([], b) : a.Circles = b();
}(this, function() {
    "use strict";
    var a = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(a) {
        setTimeout(a, 1e3 / 60);
    }, b = function(a) {
        var b = a.id;
        if (this._el = document.getElementById(b), null !== this._el) {
            this._radius = a.radius || 10, this._duration = void 0 === a.duration ? 500 : a.duration, 
            this._value = 0, this._maxValue = a.maxValue || 100, this._text = void 0 === a.text ? function(a) {
                return this.htmlifyNumber(a);
            } : a.text, this._strokeWidth = a.width || 10, this._colors = a.colors || [ "#EEE", "#F00" ], 
            this._svg = null, this._movingPath = null, this._wrapContainer = null, this._textContainer = null, 
            this._wrpClass = a.wrpClass || "circles-wrp", this._textClass = a.textClass || "circles-text", 
            this._valClass = a.valueStrokeClass || "circles-valueStroke", this._maxValClass = a.maxValueStrokeClass || "circles-maxValueStroke", 
            this._styleWrapper = a.styleWrapper === !1 ? !1 : !0, this._styleText = a.styleText === !1 ? !1 : !0;
            var c = Math.PI / 180 * 270;
            this._start = -Math.PI / 180 * 90, this._startPrecise = this._precise(this._start), 
            this._circ = c - this._start, this._generate().update(a.value || 0);
        }
    };
    return b.prototype = {
        VERSION: "0.0.6",
        _generate: function() {
            return this._svgSize = 2 * this._radius, this._radiusAdjusted = this._radius - this._strokeWidth / 2, 
            this._generateSvg()._generateText()._generateWrapper(), this._el.innerHTML = "", 
            this._el.appendChild(this._wrapContainer), this;
        },
        _setPercentage: function(a) {
            this._movingPath.setAttribute("d", this._calculatePath(a, !0)), this._textContainer.innerHTML = this._getText(this.getValueFromPercent(a));
        },
        _generateWrapper: function() {
            return this._wrapContainer = document.createElement("div"), this._wrapContainer.className = this._wrpClass, 
            this._styleWrapper && (this._wrapContainer.style.position = "relative", this._wrapContainer.style.display = "inline-block"), 
            this._wrapContainer.appendChild(this._svg), this._wrapContainer.appendChild(this._textContainer), 
            this;
        },
        _generateText: function() {
            if (this._textContainer = document.createElement("div"), this._textContainer.className = this._textClass, 
            this._styleText) {
                var a = {
                    position: "absolute",
                    top: 0,
                    left: 0,
                    textAlign: "center",
                    width: "100%",
                    fontSize: .7 * this._radius + "px",
                    height: this._svgSize + "px",
                    lineHeight: this._svgSize + "px"
                };
                for (var b in a) this._textContainer.style[b] = a[b];
            }
            return this._textContainer.innerHTML = this._getText(0), this;
        },
        _getText: function(a) {
            return this._text ? (void 0 === a && (a = this._value), a = parseFloat(a.toFixed(2)), 
            "function" == typeof this._text ? this._text.call(this, a) : this._text) : "";
        },
        _generateSvg: function() {
            return this._svg = document.createElementNS("http://www.w3.org/2000/svg", "svg"), 
            this._svg.setAttribute("xmlns", "http://www.w3.org/2000/svg"), this._svg.setAttribute("viewBox", "0 0 " + this._svgSize + " " + this._svgSize), 
            this._generatePath(100, !1, this._colors[0], this._maxValClass)._generatePath(1, !0, this._colors[1], this._valClass), 
            this._movingPath = this._svg.getElementsByTagName("path")[1], this;
        },
        _generatePath: function(a, b, c, d) {
            var e = document.createElementNS("http://www.w3.org/2000/svg", "path");
            return e.setAttribute("fill", "transparent"), e.setAttribute("stroke", c), e.setAttribute("stroke-width", this._strokeWidth), 
            e.setAttribute("d", this._calculatePath(a, b)), e.setAttribute("class", d), this._svg.appendChild(e), 
            this;
        },
        _calculatePath: function(a, b) {
            var c = this._start + a / 100 * this._circ, d = this._precise(c);
            return this._arc(d, b);
        },
        _arc: function(a, b) {
            var c = a - .001, d = a - this._startPrecise < Math.PI ? 0 : 1;
            return [ "M", this._radius + this._radiusAdjusted * Math.cos(this._startPrecise), this._radius + this._radiusAdjusted * Math.sin(this._startPrecise), "A", this._radiusAdjusted, this._radiusAdjusted, 0, d, 1, this._radius + this._radiusAdjusted * Math.cos(c), this._radius + this._radiusAdjusted * Math.sin(c), b ? "" : "Z" ].join(" ");
        },
        _precise: function(a) {
            return Math.round(1e3 * a) / 1e3;
        },
        htmlifyNumber: function(a, b, c) {
            b = b || "circles-integer", c = c || "circles-decimals";
            var d = (a + "").split("."), e = '<span class="' + b + '">' + d[0] + "</span>";
            return d.length > 1 && (e += '.<span class="' + c + '">' + d[1].substring(0, 2) + "</span>"), 
            e;
        },
        updateRadius: function(a) {
            return this._radius = a, this._generate().update(!0);
        },
        updateWidth: function(a) {
            return this._strokeWidth = a, this._generate().update(!0);
        },
        updateColors: function(a) {
            this._colors = a;
            var b = this._svg.getElementsByTagName("path");
            return b[0].setAttribute("stroke", a[0]), b[1].setAttribute("stroke", a[1]), this;
        },
        getPercent: function() {
            return 100 * this._value / this._maxValue;
        },
        getValueFromPercent: function(a) {
            return this._maxValue * a / 100;
        },
        getValue: function() {
            return this._value;
        },
        getMaxValue: function() {
            return this._maxValue;
        },
        update: function(b, c) {
            if (b === !0) return this._setPercentage(this.getPercent()), this;
            if (this._value == b || isNaN(b)) return this;
            void 0 === c && (c = this._duration);
            var d, e, f, g, h = this, i = h.getPercent(), j = 1;
            return this._value = Math.min(this._maxValue, Math.max(0, b)), c ? (d = h.getPercent(), 
            e = d > i, j += d % 1, f = Math.floor(Math.abs(d - i) / j), g = c / f, function k(b) {
                if (e ? i += j : i -= j, e && i >= d || !e && d >= i) return void a(function() {
                    h._setPercentage(d);
                });
                a(function() {
                    h._setPercentage(i);
                });
                var c = Date.now(), f = c - b;
                f >= g ? k(c) : setTimeout(function() {
                    k(Date.now());
                }, g - f);
            }(Date.now()), this) : (this._setPercentage(this.getPercent()), this);
        }
    }, b.create = function(a) {
        return new b(a);
    }, b;
});

/*!
 * jQuery-Seat-Charts v1.1.5
 * https://github.com/mateuszmarkowski/jQuery-Seat-Charts
 *
 * Copyright 2013, 2016 Mateusz Markowski
 * Released under the MIT license
 */
!function(t) {
    t.fn.seatCharts = function(s) {
        if (this.data("seatCharts")) return this.data("seatCharts");
        var e = this, a = {}, n = [], i = {
            animate: !1,
            naming: {
                top: !0,
                left: !0,
                getId: function(t, s, e) {
                    return s + "_" + e;
                },
                getLabel: function(t, s, e) {
                    return e;
                }
            },
            legend: {
                node: null,
                items: []
            },
            click: function() {
                return "available" == this.status() ? "selected" : "selected" == this.status() ? "available" : this.style();
            },
            focus: function() {
                return "available" == this.status() ? "focused" : this.style();
            },
            blur: function() {
                return this.status();
            },
            seats: {}
        }, r = function(s, e) {
            return function(n) {
                var i = this;
                i.settings = t.extend({
                    status: "available",
                    style: "available",
                    data: e.seats[n.character] || {}
                }, n), i.settings.$node = t("<div></div>"), i.settings.$node.attr({
                    id: i.settings.id,
                    role: "checkbox",
                    "aria-checked": !1,
                    focusable: !0,
                    tabIndex: -1
                }).text(i.settings.label).addClass([ "seatCharts-seat", "seatCharts-cell", "available" ].concat(i.settings.classes, "undefined" == typeof e.seats[i.settings.character] ? [] : e.seats[i.settings.character].classes).join(" ")), 
                i.data = function() {
                    return i.settings.data;
                }, i["char"] = function() {
                    return i.settings.character;
                }, i.node = function() {
                    return i.settings.$node;
                }, i.style = function() {
                    return 1 == arguments.length ? function(t) {
                        var s = i.settings.style;
                        return t == s ? s : (i.settings.status = "focused" != t ? t : i.settings.status, 
                        i.settings.$node.attr("aria-checked", "selected" == t), e.animate ? i.settings.$node.switchClass(s, t, 200) : i.settings.$node.removeClass(s).addClass(t), 
                        i.settings.style = t);
                    }(arguments[0]) : i.settings.style;
                }, i.status = function() {
                    return i.settings.status = 1 == arguments.length ? i.style(arguments[0]) : i.settings.status;
                }, function(n, r, c) {
                    t.each([ "click", "focus", "blur" ], function(t, u) {
                        i[u] = function() {
                            return "focus" == u && (void 0 !== s.attr("aria-activedescendant") && a[s.attr("aria-activedescendant")].blur(), 
                            s.attr("aria-activedescendant", c.settings.id), c.node().focus()), i.style("function" == typeof n[r][u] ? n[r][u].apply(c) : e[u].apply(c));
                        };
                    });
                }(e.seats, i.settings.character, i), i.node().on("click", i.click).on("mouseenter", i.focus).on("mouseleave", i.blur).on("keydown", function(t, e) {
                    return function(n) {
                        var i;
                        switch (n.which) {
                          case 32:
                            n.preventDefault(), t.click();
                            break;

                          case 40:
                          case 38:
                            if (n.preventDefault(), i = function r(t, s, a) {
                                var c;
                                return c = t.index(a) || 38 != n.which ? t.index(a) == t.length - 1 && 40 == n.which ? t.first() : t.eq(t.index(a) + (38 == n.which ? -1 : 1)) : t.last(), 
                                i = c.find(".seatCharts-seat,.seatCharts-space").eq(s.index(e)), i.hasClass("seatCharts-space") ? r(t, s, c) : i;
                            }(e.parents(".seatCharts-container").find(".seatCharts-row:not(.seatCharts-header)"), e.parents(".seatCharts-row:first").find(".seatCharts-seat,.seatCharts-space"), e.parents(".seatCharts-row:not(.seatCharts-header)")), 
                            !i.length) return;
                            t.blur(), a[i.attr("id")].focus(), i.focus(), s.attr("aria-activedescendant", i.attr("id"));
                            break;

                          case 37:
                          case 39:
                            if (n.preventDefault(), i = function(t) {
                                return t.index(e) || 37 != n.which ? t.index(e) == t.length - 1 && 39 == n.which ? t.first() : t.eq(t.index(e) + (37 == n.which ? -1 : 1)) : t.last();
                            }(e.parents(".seatCharts-container:first").find(".seatCharts-seat:not(.seatCharts-space)")), 
                            !i.length) return;
                            t.blur(), a[i.attr("id")].focus(), i.focus(), s.attr("aria-activedescendant", i.attr("id"));
                        }
                    };
                }(i, i.node()));
            };
        }(e, i);
        if (e.addClass("seatCharts-container"), t.extend(!0, i, s), i.naming.rows = i.naming.rows || function(t) {
            for (var s = [], e = 1; t >= e; e++) s.push(e);
            return s;
        }(i.map.length), i.naming.columns = i.naming.columns || function(t) {
            for (var s = [], e = 1; t >= e; e++) s.push(e);
            return s;
        }(i.map[0].split("").length), i.naming.top) {
            var c = t("<div></div>").addClass("seatCharts-row seatCharts-header");
            i.naming.left && c.append(t("<div></div>").addClass("seatCharts-cell")), t.each(i.naming.columns, function(s, e) {
                c.append(t("<div></div>").addClass("seatCharts-cell").text(e));
            });
        }
        return e.append(c), t.each(i.map, function(s, c) {
            var u = t("<div></div>").addClass("seatCharts-row");
            i.naming.left && u.append(t("<div></div>").addClass("seatCharts-cell seatCharts-space").text(i.naming.rows[s])), 
            t.each(c.match(/[a-z_]{1}(\[[0-9a-z_]{0,}(,[0-9a-z_ ]+)?\])?/gi), function(e, c) {
                var h = c.match(/([a-z_]{1})(\[([0-9a-z_ ,]+)\])?/i), d = h[1], o = "undefined" != typeof h[3] ? h[3].split(",") : [], l = o.length ? o[0] : null, f = 2 === o.length ? o[1] : null;
                u.append("_" != d ? function(t) {
                    i.seats[d] = d in i.seats ? i.seats[d] : {};
                    var c = l ? l : t.getId(d, t.rows[s], t.columns[e]);
                    return a[c] = new r({
                        id: c,
                        label: f ? f : t.getLabel(d, t.rows[s], t.columns[e]),
                        row: s,
                        column: e,
                        character: d
                    }), n.push(c), a[c].node();
                }(i.naming) : t("<div></div>").addClass("seatCharts-cell seatCharts-space"));
            }), e.append(u);
        }), i.legend.items.length ? function(s) {
            var a = (s.node || t("<div></div>").insertAfter(e)).addClass("seatCharts-legend"), n = t("<ul></ul>").addClass("seatCharts-legendList").appendTo(a);
            return t.each(s.items, function(s, e) {
                n.append(t("<li></li>").addClass("seatCharts-legendItem").append(t("<div></div>").addClass([ "seatCharts-seat", "seatCharts-cell", e[1] ].concat(i.classes, "undefined" == typeof i.seats[e[0]] ? [] : i.seats[e[0]].classes).join(" "))).append(t("<span></span>").addClass("seatCharts-legendDescription").text(e[2])));
            }), a;
        }(i.legend) : null, e.attr({
            tabIndex: 0
        }), e.focus(function() {
            e.attr("aria-activedescendant") && a[e.attr("aria-activedescendant")].blur(), e.find(".seatCharts-seat:not(.seatCharts-space):first").focus(), 
            a[n[0]].focus();
        }), e.data("seatCharts", {
            seats: a,
            seatIds: n,
            status: function() {
                var s = this;
                return 1 == arguments.length ? s.seats[arguments[0]].status() : function(e, a) {
                    return "string" == typeof e ? s.seats[e].status(a) : function() {
                        t.each(e, function(t, e) {
                            s.seats[e].status(a);
                        });
                    }();
                }(arguments[0], arguments[1]);
            },
            each: function(t) {
                var s = this;
                for (var e in s.seats) if (!1 === t.call(s.seats[e], e)) return e;
                return !0;
            },
            node: function() {
                var s = this;
                return t("#" + s.seatIds.join(",#"));
            },
            find: function(t) {
                var s = this, e = s.set();
                return t instanceof RegExp ? function() {
                    return s.each(function(s) {
                        s.match(t) && e.push(s, this);
                    }), e;
                }() : 1 == t.length ? function(t) {
                    return s.each(function() {
                        this["char"]() == t && e.push(this.settings.id, this);
                    }), e;
                }(t) : function() {
                    return t.indexOf(".") > -1 ? function() {
                        var a = t.split(".");
                        return s.each(function(t) {
                            this["char"]() == a[0] && this.status() == a[1] && e.push(this.settings.id, this);
                        }), e;
                    }() : function() {
                        return s.each(function() {
                            this.status() == t && e.push(this.settings.id, this);
                        }), e;
                    }();
                }();
            },
            set: function u() {
                var s = this;
                return {
                    seats: [],
                    seatIds: [],
                    length: 0,
                    status: function() {
                        var s = arguments, e = this;
                        return 1 == this.length && 0 == s.length ? this.seats[0].status() : function() {
                            t.each(e.seats, function() {
                                this.status.apply(this, s);
                            });
                        }();
                    },
                    node: function() {
                        return s.node.call(this);
                    },
                    each: function() {
                        return s.each.call(this, arguments[0]);
                    },
                    get: function() {
                        return s.get.call(this, arguments[0]);
                    },
                    find: function() {
                        return s.find.call(this, arguments[0]);
                    },
                    set: function() {
                        return u.call(s);
                    },
                    push: function(t, s) {
                        this.seats.push(s), this.seatIds.push(t), ++this.length;
                    }
                };
            },
            get: function(s) {
                var e = this;
                return "string" == typeof s ? e.seats[s] : function() {
                    var a = e.set();
                    return t.each(s, function(t, s) {
                        "object" == typeof e.seats[s] && a.push(s, e.seats[s]);
                    }), a;
                }();
            }
        }), e.data("seatCharts");
    };
}(jQuery);

/*
 * jQuery Easing v1.3 - http://gsgd.co.uk/sandbox/jquery/easing/
 *
 * Uses the built in easing capabilities added In jQuery 1.1
 * to offer multiple easing options
 *
 * TERMS OF USE - EASING EQUATIONS
 * 
 * Open source under the BSD License. 
 * 
 * Copyright Â© 2001 Robert Penner
 * All rights reserved.
 *
 * TERMS OF USE - jQuery Easing
 * 
 * Open source under the BSD License.  
 * 
 * Copyright Â© 2008 George McGinley Smith
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * Neither the name of the author nor the names of contributors may be used to endorse 
 * or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *  GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 *
*/
jQuery.easing.jswing = jQuery.easing.swing;

jQuery.extend(jQuery.easing, {
    def: "easeOutQuad",
    swing: function(e, f, a, h, g) {
        return jQuery.easing[jQuery.easing.def](e, f, a, h, g);
    },
    easeInQuad: function(e, f, a, h, g) {
        return h * (f /= g) * f + a;
    },
    easeOutQuad: function(e, f, a, h, g) {
        return -h * (f /= g) * (f - 2) + a;
    },
    easeInOutQuad: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f + a;
        }
        return -h / 2 * (--f * (f - 2) - 1) + a;
    },
    easeInCubic: function(e, f, a, h, g) {
        return h * (f /= g) * f * f + a;
    },
    easeOutCubic: function(e, f, a, h, g) {
        return h * ((f = f / g - 1) * f * f + 1) + a;
    },
    easeInOutCubic: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f + a;
        }
        return h / 2 * ((f -= 2) * f * f + 2) + a;
    },
    easeInQuart: function(e, f, a, h, g) {
        return h * (f /= g) * f * f * f + a;
    },
    easeOutQuart: function(e, f, a, h, g) {
        return -h * ((f = f / g - 1) * f * f * f - 1) + a;
    },
    easeInOutQuart: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f * f + a;
        }
        return -h / 2 * ((f -= 2) * f * f * f - 2) + a;
    },
    easeInQuint: function(e, f, a, h, g) {
        return h * (f /= g) * f * f * f * f + a;
    },
    easeOutQuint: function(e, f, a, h, g) {
        return h * ((f = f / g - 1) * f * f * f * f + 1) + a;
    },
    easeInOutQuint: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return h / 2 * f * f * f * f * f + a;
        }
        return h / 2 * ((f -= 2) * f * f * f * f + 2) + a;
    },
    easeInSine: function(e, f, a, h, g) {
        return -h * Math.cos(f / g * (Math.PI / 2)) + h + a;
    },
    easeOutSine: function(e, f, a, h, g) {
        return h * Math.sin(f / g * (Math.PI / 2)) + a;
    },
    easeInOutSine: function(e, f, a, h, g) {
        return -h / 2 * (Math.cos(Math.PI * f / g) - 1) + a;
    },
    easeInExpo: function(e, f, a, h, g) {
        return f == 0 ? a : h * Math.pow(2, 10 * (f / g - 1)) + a;
    },
    easeOutExpo: function(e, f, a, h, g) {
        return f == g ? a + h : h * (-Math.pow(2, -10 * f / g) + 1) + a;
    },
    easeInOutExpo: function(e, f, a, h, g) {
        if (f == 0) {
            return a;
        }
        if (f == g) {
            return a + h;
        }
        if ((f /= g / 2) < 1) {
            return h / 2 * Math.pow(2, 10 * (f - 1)) + a;
        }
        return h / 2 * (-Math.pow(2, -10 * --f) + 2) + a;
    },
    easeInCirc: function(e, f, a, h, g) {
        return -h * (Math.sqrt(1 - (f /= g) * f) - 1) + a;
    },
    easeOutCirc: function(e, f, a, h, g) {
        return h * Math.sqrt(1 - (f = f / g - 1) * f) + a;
    },
    easeInOutCirc: function(e, f, a, h, g) {
        if ((f /= g / 2) < 1) {
            return -h / 2 * (Math.sqrt(1 - f * f) - 1) + a;
        }
        return h / 2 * (Math.sqrt(1 - (f -= 2) * f) + 1) + a;
    },
    easeInElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e;
        }
        if ((h /= k) == 1) {
            return e + l;
        }
        if (!j) {
            j = k * .3;
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4;
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g);
        }
        return -(g * Math.pow(2, 10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j)) + e;
    },
    easeOutElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e;
        }
        if ((h /= k) == 1) {
            return e + l;
        }
        if (!j) {
            j = k * .3;
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4;
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g);
        }
        return g * Math.pow(2, -10 * h) * Math.sin((h * k - i) * (2 * Math.PI) / j) + l + e;
    },
    easeInOutElastic: function(f, h, e, l, k) {
        var i = 1.70158;
        var j = 0;
        var g = l;
        if (h == 0) {
            return e;
        }
        if ((h /= k / 2) == 2) {
            return e + l;
        }
        if (!j) {
            j = k * (.3 * 1.5);
        }
        if (g < Math.abs(l)) {
            g = l;
            var i = j / 4;
        } else {
            var i = j / (2 * Math.PI) * Math.asin(l / g);
        }
        if (h < 1) {
            return -.5 * (g * Math.pow(2, 10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j)) + e;
        }
        return g * Math.pow(2, -10 * (h -= 1)) * Math.sin((h * k - i) * (2 * Math.PI) / j) * .5 + l + e;
    },
    easeInBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158;
        }
        return i * (f /= h) * f * ((g + 1) * f - g) + a;
    },
    easeOutBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158;
        }
        return i * ((f = f / h - 1) * f * ((g + 1) * f + g) + 1) + a;
    },
    easeInOutBack: function(e, f, a, i, h, g) {
        if (g == undefined) {
            g = 1.70158;
        }
        if ((f /= h / 2) < 1) {
            return i / 2 * (f * f * (((g *= 1.525) + 1) * f - g)) + a;
        }
        return i / 2 * ((f -= 2) * f * (((g *= 1.525) + 1) * f + g) + 2) + a;
    },
    easeInBounce: function(e, f, a, h, g) {
        return h - jQuery.easing.easeOutBounce(e, g - f, 0, h, g) + a;
    },
    easeOutBounce: function(e, f, a, h, g) {
        if ((f /= g) < 1 / 2.75) {
            return h * (7.5625 * f * f) + a;
        } else {
            if (f < 2 / 2.75) {
                return h * (7.5625 * (f -= 1.5 / 2.75) * f + .75) + a;
            } else {
                if (f < 2.5 / 2.75) {
                    return h * (7.5625 * (f -= 2.25 / 2.75) * f + .9375) + a;
                } else {
                    return h * (7.5625 * (f -= 2.625 / 2.75) * f + .984375) + a;
                }
            }
        }
    },
    easeInOutBounce: function(e, f, a, h, g) {
        if (f < g / 2) {
            return jQuery.easing.easeInBounce(e, f * 2, 0, h, g) * .5 + a;
        }
        return jQuery.easing.easeOutBounce(e, f * 2 - g, 0, h, g) * .5 + h * .5 + a;
    }
});

/*!
 * Bootstrap v3.3.7 (http://getbootstrap.com)
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under the MIT license
 */
if ("undefined" == typeof jQuery) throw new Error("Bootstrap's JavaScript requires jQuery");

+function(a) {
    "use strict";
    var b = a.fn.jquery.split(" ")[0].split(".");
    if (b[0] < 2 && b[1] < 9 || 1 == b[0] && 9 == b[1] && b[2] < 1 || b[0] > 3) throw new Error("Bootstrap's JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4");
}(jQuery), +function(a) {
    "use strict";
    function b() {
        var a = document.createElement("bootstrap"), b = {
            WebkitTransition: "webkitTransitionEnd",
            MozTransition: "transitionend",
            OTransition: "oTransitionEnd otransitionend",
            transition: "transitionend"
        };
        for (var c in b) if (void 0 !== a.style[c]) return {
            end: b[c]
        };
        return !1;
    }
    a.fn.emulateTransitionEnd = function(b) {
        var c = !1, d = this;
        a(this).one("bsTransitionEnd", function() {
            c = !0;
        });
        var e = function() {
            c || a(d).trigger(a.support.transition.end);
        };
        return setTimeout(e, b), this;
    }, a(function() {
        a.support.transition = b(), a.support.transition && (a.event.special.bsTransitionEnd = {
            bindType: a.support.transition.end,
            delegateType: a.support.transition.end,
            handle: function(b) {
                if (a(b.target).is(this)) return b.handleObj.handler.apply(this, arguments);
            }
        });
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var c = a(this), e = c.data("bs.alert");
            e || c.data("bs.alert", e = new d(this)), "string" == typeof b && e[b].call(c);
        });
    }
    var c = '[data-dismiss="alert"]', d = function(b) {
        a(b).on("click", c, this.close);
    };
    d.VERSION = "3.3.7", d.TRANSITION_DURATION = 150, d.prototype.close = function(b) {
        function c() {
            g.detach().trigger("closed.bs.alert").remove();
        }
        var e = a(this), f = e.attr("data-target");
        f || (f = e.attr("href"), f = f && f.replace(/.*(?=#[^\s]*$)/, ""));
        var g = a("#" === f ? [] : f);
        b && b.preventDefault(), g.length || (g = e.closest(".alert")), g.trigger(b = a.Event("close.bs.alert")), 
        b.isDefaultPrevented() || (g.removeClass("in"), a.support.transition && g.hasClass("fade") ? g.one("bsTransitionEnd", c).emulateTransitionEnd(d.TRANSITION_DURATION) : c());
    };
    var e = a.fn.alert;
    a.fn.alert = b, a.fn.alert.Constructor = d, a.fn.alert.noConflict = function() {
        return a.fn.alert = e, this;
    }, a(document).on("click.bs.alert.data-api", c, d.prototype.close);
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.button"), f = "object" == typeof b && b;
            e || d.data("bs.button", e = new c(this, f)), "toggle" == b ? e.toggle() : b && e.setState(b);
        });
    }
    var c = function(b, d) {
        this.$element = a(b), this.options = a.extend({}, c.DEFAULTS, d), this.isLoading = !1;
    };
    c.VERSION = "3.3.7", c.DEFAULTS = {
        loadingText: "loading..."
    }, c.prototype.setState = function(b) {
        var c = "disabled", d = this.$element, e = d.is("input") ? "val" : "html", f = d.data();
        b += "Text", null == f.resetText && d.data("resetText", d[e]()), setTimeout(a.proxy(function() {
            d[e](null == f[b] ? this.options[b] : f[b]), "loadingText" == b ? (this.isLoading = !0, 
            d.addClass(c).attr(c, c).prop(c, !0)) : this.isLoading && (this.isLoading = !1, 
            d.removeClass(c).removeAttr(c).prop(c, !1));
        }, this), 0);
    }, c.prototype.toggle = function() {
        var a = !0, b = this.$element.closest('[data-toggle="buttons"]');
        if (b.length) {
            var c = this.$element.find("input");
            "radio" == c.prop("type") ? (c.prop("checked") && (a = !1), b.find(".active").removeClass("active"), 
            this.$element.addClass("active")) : "checkbox" == c.prop("type") && (c.prop("checked") !== this.$element.hasClass("active") && (a = !1), 
            this.$element.toggleClass("active")), c.prop("checked", this.$element.hasClass("active")), 
            a && c.trigger("change");
        } else this.$element.attr("aria-pressed", !this.$element.hasClass("active")), this.$element.toggleClass("active");
    };
    var d = a.fn.button;
    a.fn.button = b, a.fn.button.Constructor = c, a.fn.button.noConflict = function() {
        return a.fn.button = d, this;
    }, a(document).on("click.bs.button.data-api", '[data-toggle^="button"]', function(c) {
        var d = a(c.target).closest(".btn");
        b.call(d, "toggle"), a(c.target).is('input[type="radio"], input[type="checkbox"]') || (c.preventDefault(), 
        d.is("input,button") ? d.trigger("focus") : d.find("input:visible,button:visible").first().trigger("focus"));
    }).on("focus.bs.button.data-api blur.bs.button.data-api", '[data-toggle^="button"]', function(b) {
        a(b.target).closest(".btn").toggleClass("focus", /^focus(in)?$/.test(b.type));
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.carousel"), f = a.extend({}, c.DEFAULTS, d.data(), "object" == typeof b && b), g = "string" == typeof b ? b : f.slide;
            e || d.data("bs.carousel", e = new c(this, f)), "number" == typeof b ? e.to(b) : g ? e[g]() : f.interval && e.pause().cycle();
        });
    }
    var c = function(b, c) {
        this.$element = a(b), this.$indicators = this.$element.find(".carousel-indicators"), 
        this.options = c, this.paused = null, this.sliding = null, this.interval = null, 
        this.$active = null, this.$items = null, this.options.keyboard && this.$element.on("keydown.bs.carousel", a.proxy(this.keydown, this)), 
        "hover" == this.options.pause && !("ontouchstart" in document.documentElement) && this.$element.on("mouseenter.bs.carousel", a.proxy(this.pause, this)).on("mouseleave.bs.carousel", a.proxy(this.cycle, this));
    };
    c.VERSION = "3.3.7", c.TRANSITION_DURATION = 600, c.DEFAULTS = {
        interval: 5e3,
        pause: "hover",
        wrap: !0,
        keyboard: !0
    }, c.prototype.keydown = function(a) {
        if (!/input|textarea/i.test(a.target.tagName)) {
            switch (a.which) {
              case 37:
                this.prev();
                break;

              case 39:
                this.next();
                break;

              default:
                return;
            }
            a.preventDefault();
        }
    }, c.prototype.cycle = function(b) {
        return b || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval(a.proxy(this.next, this), this.options.interval)), 
        this;
    }, c.prototype.getItemIndex = function(a) {
        return this.$items = a.parent().children(".item"), this.$items.index(a || this.$active);
    }, c.prototype.getItemForDirection = function(a, b) {
        var c = this.getItemIndex(b), d = "prev" == a && 0 === c || "next" == a && c == this.$items.length - 1;
        if (d && !this.options.wrap) return b;
        var e = "prev" == a ? -1 : 1, f = (c + e) % this.$items.length;
        return this.$items.eq(f);
    }, c.prototype.to = function(a) {
        var b = this, c = this.getItemIndex(this.$active = this.$element.find(".item.active"));
        if (!(a > this.$items.length - 1 || a < 0)) return this.sliding ? this.$element.one("slid.bs.carousel", function() {
            b.to(a);
        }) : c == a ? this.pause().cycle() : this.slide(a > c ? "next" : "prev", this.$items.eq(a));
    }, c.prototype.pause = function(b) {
        return b || (this.paused = !0), this.$element.find(".next, .prev").length && a.support.transition && (this.$element.trigger(a.support.transition.end), 
        this.cycle(!0)), this.interval = clearInterval(this.interval), this;
    }, c.prototype.next = function() {
        if (!this.sliding) return this.slide("next");
    }, c.prototype.prev = function() {
        if (!this.sliding) return this.slide("prev");
    }, c.prototype.slide = function(b, d) {
        var e = this.$element.find(".item.active"), f = d || this.getItemForDirection(b, e), g = this.interval, h = "next" == b ? "left" : "right", i = this;
        if (f.hasClass("active")) return this.sliding = !1;
        var j = f[0], k = a.Event("slide.bs.carousel", {
            relatedTarget: j,
            direction: h
        });
        if (this.$element.trigger(k), !k.isDefaultPrevented()) {
            if (this.sliding = !0, g && this.pause(), this.$indicators.length) {
                this.$indicators.find(".active").removeClass("active");
                var l = a(this.$indicators.children()[this.getItemIndex(f)]);
                l && l.addClass("active");
            }
            var m = a.Event("slid.bs.carousel", {
                relatedTarget: j,
                direction: h
            });
            return a.support.transition && this.$element.hasClass("slide") ? (f.addClass(b), 
            f[0].offsetWidth, e.addClass(h), f.addClass(h), e.one("bsTransitionEnd", function() {
                f.removeClass([ b, h ].join(" ")).addClass("active"), e.removeClass([ "active", h ].join(" ")), 
                i.sliding = !1, setTimeout(function() {
                    i.$element.trigger(m);
                }, 0);
            }).emulateTransitionEnd(c.TRANSITION_DURATION)) : (e.removeClass("active"), f.addClass("active"), 
            this.sliding = !1, this.$element.trigger(m)), g && this.cycle(), this;
        }
    };
    var d = a.fn.carousel;
    a.fn.carousel = b, a.fn.carousel.Constructor = c, a.fn.carousel.noConflict = function() {
        return a.fn.carousel = d, this;
    };
    var e = function(c) {
        var d, e = a(this), f = a(e.attr("data-target") || (d = e.attr("href")) && d.replace(/.*(?=#[^\s]+$)/, ""));
        if (f.hasClass("carousel")) {
            var g = a.extend({}, f.data(), e.data()), h = e.attr("data-slide-to");
            h && (g.interval = !1), b.call(f, g), h && f.data("bs.carousel").to(h), c.preventDefault();
        }
    };
    a(document).on("click.bs.carousel.data-api", "[data-slide]", e).on("click.bs.carousel.data-api", "[data-slide-to]", e), 
    a(window).on("load", function() {
        a('[data-ride="carousel"]').each(function() {
            var c = a(this);
            b.call(c, c.data());
        });
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        var c, d = b.attr("data-target") || (c = b.attr("href")) && c.replace(/.*(?=#[^\s]+$)/, "");
        return a(d);
    }
    function c(b) {
        return this.each(function() {
            var c = a(this), e = c.data("bs.collapse"), f = a.extend({}, d.DEFAULTS, c.data(), "object" == typeof b && b);
            !e && f.toggle && /show|hide/.test(b) && (f.toggle = !1), e || c.data("bs.collapse", e = new d(this, f)), 
            "string" == typeof b && e[b]();
        });
    }
    var d = function(b, c) {
        this.$element = a(b), this.options = a.extend({}, d.DEFAULTS, c), this.$trigger = a('[data-toggle="collapse"][href="#' + b.id + '"],[data-toggle="collapse"][data-target="#' + b.id + '"]'), 
        this.transitioning = null, this.options.parent ? this.$parent = this.getParent() : this.addAriaAndCollapsedClass(this.$element, this.$trigger), 
        this.options.toggle && this.toggle();
    };
    d.VERSION = "3.3.7", d.TRANSITION_DURATION = 350, d.DEFAULTS = {
        toggle: !0
    }, d.prototype.dimension = function() {
        var a = this.$element.hasClass("width");
        return a ? "width" : "height";
    }, d.prototype.show = function() {
        if (!this.transitioning && !this.$element.hasClass("in")) {
            var b, e = this.$parent && this.$parent.children(".panel").children(".in, .collapsing");
            if (!(e && e.length && (b = e.data("bs.collapse"), b && b.transitioning))) {
                var f = a.Event("show.bs.collapse");
                if (this.$element.trigger(f), !f.isDefaultPrevented()) {
                    e && e.length && (c.call(e, "hide"), b || e.data("bs.collapse", null));
                    var g = this.dimension();
                    this.$element.removeClass("collapse").addClass("collapsing")[g](0).attr("aria-expanded", !0), 
                    this.$trigger.removeClass("collapsed").attr("aria-expanded", !0), this.transitioning = 1;
                    var h = function() {
                        this.$element.removeClass("collapsing").addClass("collapse in")[g](""), this.transitioning = 0, 
                        this.$element.trigger("shown.bs.collapse");
                    };
                    if (!a.support.transition) return h.call(this);
                    var i = a.camelCase([ "scroll", g ].join("-"));
                    this.$element.one("bsTransitionEnd", a.proxy(h, this)).emulateTransitionEnd(d.TRANSITION_DURATION)[g](this.$element[0][i]);
                }
            }
        }
    }, d.prototype.hide = function() {
        if (!this.transitioning && this.$element.hasClass("in")) {
            var b = a.Event("hide.bs.collapse");
            if (this.$element.trigger(b), !b.isDefaultPrevented()) {
                var c = this.dimension();
                this.$element[c](this.$element[c]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse in").attr("aria-expanded", !1), 
                this.$trigger.addClass("collapsed").attr("aria-expanded", !1), this.transitioning = 1;
                var e = function() {
                    this.transitioning = 0, this.$element.removeClass("collapsing").addClass("collapse").trigger("hidden.bs.collapse");
                };
                return a.support.transition ? void this.$element[c](0).one("bsTransitionEnd", a.proxy(e, this)).emulateTransitionEnd(d.TRANSITION_DURATION) : e.call(this);
            }
        }
    }, d.prototype.toggle = function() {
        this[this.$element.hasClass("in") ? "hide" : "show"]();
    }, d.prototype.getParent = function() {
        return a(this.options.parent).find('[data-toggle="collapse"][data-parent="' + this.options.parent + '"]').each(a.proxy(function(c, d) {
            var e = a(d);
            this.addAriaAndCollapsedClass(b(e), e);
        }, this)).end();
    }, d.prototype.addAriaAndCollapsedClass = function(a, b) {
        var c = a.hasClass("in");
        a.attr("aria-expanded", c), b.toggleClass("collapsed", !c).attr("aria-expanded", c);
    };
    var e = a.fn.collapse;
    a.fn.collapse = c, a.fn.collapse.Constructor = d, a.fn.collapse.noConflict = function() {
        return a.fn.collapse = e, this;
    }, a(document).on("click.bs.collapse.data-api", '[data-toggle="collapse"]', function(d) {
        var e = a(this);
        e.attr("data-target") || d.preventDefault();
        var f = b(e), g = f.data("bs.collapse"), h = g ? "toggle" : e.data();
        c.call(f, h);
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        var c = b.attr("data-target");
        c || (c = b.attr("href"), c = c && /#[A-Za-z]/.test(c) && c.replace(/.*(?=#[^\s]*$)/, ""));
        var d = c && a(c);
        return d && d.length ? d : b.parent();
    }
    function c(c) {
        c && 3 === c.which || (a(e).remove(), a(f).each(function() {
            var d = a(this), e = b(d), f = {
                relatedTarget: this
            };
            e.hasClass("open") && (c && "click" == c.type && /input|textarea/i.test(c.target.tagName) && a.contains(e[0], c.target) || (e.trigger(c = a.Event("hide.bs.dropdown", f)), 
            c.isDefaultPrevented() || (d.attr("aria-expanded", "false"), e.removeClass("open").trigger(a.Event("hidden.bs.dropdown", f)))));
        }));
    }
    function d(b) {
        return this.each(function() {
            var c = a(this), d = c.data("bs.dropdown");
            d || c.data("bs.dropdown", d = new g(this)), "string" == typeof b && d[b].call(c);
        });
    }
    var e = ".dropdown-backdrop", f = '[data-toggle="dropdown"]', g = function(b) {
        a(b).on("click.bs.dropdown", this.toggle);
    };
    g.VERSION = "3.3.7", g.prototype.toggle = function(d) {
        var e = a(this);
        if (!e.is(".disabled, :disabled")) {
            var f = b(e), g = f.hasClass("open");
            if (c(), !g) {
                "ontouchstart" in document.documentElement && !f.closest(".navbar-nav").length && a(document.createElement("div")).addClass("dropdown-backdrop").insertAfter(a(this)).on("click", c);
                var h = {
                    relatedTarget: this
                };
                if (f.trigger(d = a.Event("show.bs.dropdown", h)), d.isDefaultPrevented()) return;
                e.trigger("focus").attr("aria-expanded", "true"), f.toggleClass("open").trigger(a.Event("shown.bs.dropdown", h));
            }
            return !1;
        }
    }, g.prototype.keydown = function(c) {
        if (/(38|40|27|32)/.test(c.which) && !/input|textarea/i.test(c.target.tagName)) {
            var d = a(this);
            if (c.preventDefault(), c.stopPropagation(), !d.is(".disabled, :disabled")) {
                var e = b(d), g = e.hasClass("open");
                if (!g && 27 != c.which || g && 27 == c.which) return 27 == c.which && e.find(f).trigger("focus"), 
                d.trigger("click");
                var h = " li:not(.disabled):visible a", i = e.find(".dropdown-menu" + h);
                if (i.length) {
                    var j = i.index(c.target);
                    38 == c.which && j > 0 && j--, 40 == c.which && j < i.length - 1 && j++, ~j || (j = 0), 
                    i.eq(j).trigger("focus");
                }
            }
        }
    };
    var h = a.fn.dropdown;
    a.fn.dropdown = d, a.fn.dropdown.Constructor = g, a.fn.dropdown.noConflict = function() {
        return a.fn.dropdown = h, this;
    }, a(document).on("click.bs.dropdown.data-api", c).on("click.bs.dropdown.data-api", ".dropdown form", function(a) {
        a.stopPropagation();
    }).on("click.bs.dropdown.data-api", f, g.prototype.toggle).on("keydown.bs.dropdown.data-api", f, g.prototype.keydown).on("keydown.bs.dropdown.data-api", ".dropdown-menu", g.prototype.keydown);
}(jQuery), +function(a) {
    "use strict";
    function b(b, d) {
        return this.each(function() {
            var e = a(this), f = e.data("bs.modal"), g = a.extend({}, c.DEFAULTS, e.data(), "object" == typeof b && b);
            f || e.data("bs.modal", f = new c(this, g)), "string" == typeof b ? f[b](d) : g.show && f.show(d);
        });
    }
    var c = function(b, c) {
        this.options = c, this.$body = a(document.body), this.$element = a(b), this.$dialog = this.$element.find(".modal-dialog"), 
        this.$backdrop = null, this.isShown = null, this.originalBodyPad = null, this.scrollbarWidth = 0, 
        this.ignoreBackdropClick = !1, this.options.remote && this.$element.find(".modal-content").load(this.options.remote, a.proxy(function() {
            this.$element.trigger("loaded.bs.modal");
        }, this));
    };
    c.VERSION = "3.3.7", c.TRANSITION_DURATION = 300, c.BACKDROP_TRANSITION_DURATION = 150, 
    c.DEFAULTS = {
        backdrop: !0,
        keyboard: !0,
        show: !0
    }, c.prototype.toggle = function(a) {
        return this.isShown ? this.hide() : this.show(a);
    }, c.prototype.show = function(b) {
        var d = this, e = a.Event("show.bs.modal", {
            relatedTarget: b
        });
        this.$element.trigger(e), this.isShown || e.isDefaultPrevented() || (this.isShown = !0, 
        this.checkScrollbar(), this.setScrollbar(), this.$body.addClass("modal-open"), this.escape(), 
        this.resize(), this.$element.on("click.dismiss.bs.modal", '[data-dismiss="modal"]', a.proxy(this.hide, this)), 
        this.$dialog.on("mousedown.dismiss.bs.modal", function() {
            d.$element.one("mouseup.dismiss.bs.modal", function(b) {
                a(b.target).is(d.$element) && (d.ignoreBackdropClick = !0);
            });
        }), this.backdrop(function() {
            var e = a.support.transition && d.$element.hasClass("fade");
            d.$element.parent().length || d.$element.appendTo(d.$body), d.$element.show().scrollTop(0), 
            d.adjustDialog(), e && d.$element[0].offsetWidth, d.$element.addClass("in"), d.enforceFocus();
            var f = a.Event("shown.bs.modal", {
                relatedTarget: b
            });
            e ? d.$dialog.one("bsTransitionEnd", function() {
                d.$element.trigger("focus").trigger(f);
            }).emulateTransitionEnd(c.TRANSITION_DURATION) : d.$element.trigger("focus").trigger(f);
        }));
    }, c.prototype.hide = function(b) {
        b && b.preventDefault(), b = a.Event("hide.bs.modal"), this.$element.trigger(b), 
        this.isShown && !b.isDefaultPrevented() && (this.isShown = !1, this.escape(), this.resize(), 
        a(document).off("focusin.bs.modal"), this.$element.removeClass("in").off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal"), 
        this.$dialog.off("mousedown.dismiss.bs.modal"), a.support.transition && this.$element.hasClass("fade") ? this.$element.one("bsTransitionEnd", a.proxy(this.hideModal, this)).emulateTransitionEnd(c.TRANSITION_DURATION) : this.hideModal());
    }, c.prototype.enforceFocus = function() {
        a(document).off("focusin.bs.modal").on("focusin.bs.modal", a.proxy(function(a) {
            document === a.target || this.$element[0] === a.target || this.$element.has(a.target).length || this.$element.trigger("focus");
        }, this));
    }, c.prototype.escape = function() {
        this.isShown && this.options.keyboard ? this.$element.on("keydown.dismiss.bs.modal", a.proxy(function(a) {
            27 == a.which && this.hide();
        }, this)) : this.isShown || this.$element.off("keydown.dismiss.bs.modal");
    }, c.prototype.resize = function() {
        this.isShown ? a(window).on("resize.bs.modal", a.proxy(this.handleUpdate, this)) : a(window).off("resize.bs.modal");
    }, c.prototype.hideModal = function() {
        var a = this;
        this.$element.hide(), this.backdrop(function() {
            a.$body.removeClass("modal-open"), a.resetAdjustments(), a.resetScrollbar(), a.$element.trigger("hidden.bs.modal");
        });
    }, c.prototype.removeBackdrop = function() {
        this.$backdrop && this.$backdrop.remove(), this.$backdrop = null;
    }, c.prototype.backdrop = function(b) {
        var d = this, e = this.$element.hasClass("fade") ? "fade" : "";
        if (this.isShown && this.options.backdrop) {
            var f = a.support.transition && e;
            if (this.$backdrop = a(document.createElement("div")).addClass("modal-backdrop " + e).appendTo(this.$body), 
            this.$element.on("click.dismiss.bs.modal", a.proxy(function(a) {
                return this.ignoreBackdropClick ? void (this.ignoreBackdropClick = !1) : void (a.target === a.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus() : this.hide()));
            }, this)), f && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !b) return;
            f ? this.$backdrop.one("bsTransitionEnd", b).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION) : b();
        } else if (!this.isShown && this.$backdrop) {
            this.$backdrop.removeClass("in");
            var g = function() {
                d.removeBackdrop(), b && b();
            };
            a.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one("bsTransitionEnd", g).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION) : g();
        } else b && b();
    }, c.prototype.handleUpdate = function() {
        this.adjustDialog();
    }, c.prototype.adjustDialog = function() {
        var a = this.$element[0].scrollHeight > document.documentElement.clientHeight;
        this.$element.css({
            paddingLeft: !this.bodyIsOverflowing && a ? this.scrollbarWidth : "",
            paddingRight: this.bodyIsOverflowing && !a ? this.scrollbarWidth : ""
        });
    }, c.prototype.resetAdjustments = function() {
        this.$element.css({
            paddingLeft: "",
            paddingRight: ""
        });
    }, c.prototype.checkScrollbar = function() {
        var a = window.innerWidth;
        if (!a) {
            var b = document.documentElement.getBoundingClientRect();
            a = b.right - Math.abs(b.left);
        }
        this.bodyIsOverflowing = document.body.clientWidth < a, this.scrollbarWidth = this.measureScrollbar();
    }, c.prototype.setScrollbar = function() {
        var a = parseInt(this.$body.css("padding-right") || 0, 10);
        this.originalBodyPad = document.body.style.paddingRight || "", this.bodyIsOverflowing && this.$body.css("padding-right", a + this.scrollbarWidth);
    }, c.prototype.resetScrollbar = function() {
        this.$body.css("padding-right", this.originalBodyPad);
    }, c.prototype.measureScrollbar = function() {
        var a = document.createElement("div");
        a.className = "modal-scrollbar-measure", this.$body.append(a);
        var b = a.offsetWidth - a.clientWidth;
        return this.$body[0].removeChild(a), b;
    };
    var d = a.fn.modal;
    a.fn.modal = b, a.fn.modal.Constructor = c, a.fn.modal.noConflict = function() {
        return a.fn.modal = d, this;
    }, a(document).on("click.bs.modal.data-api", '[data-toggle="modal"]', function(c) {
        var d = a(this), e = d.attr("href"), f = a(d.attr("data-target") || e && e.replace(/.*(?=#[^\s]+$)/, "")), g = f.data("bs.modal") ? "toggle" : a.extend({
            remote: !/#/.test(e) && e
        }, f.data(), d.data());
        d.is("a") && c.preventDefault(), f.one("show.bs.modal", function(a) {
            a.isDefaultPrevented() || f.one("hidden.bs.modal", function() {
                d.is(":visible") && d.trigger("focus");
            });
        }), b.call(f, g, this);
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.tooltip"), f = "object" == typeof b && b;
            !e && /destroy|hide/.test(b) || (e || d.data("bs.tooltip", e = new c(this, f)), 
            "string" == typeof b && e[b]());
        });
    }
    var c = function(a, b) {
        this.type = null, this.options = null, this.enabled = null, this.timeout = null, 
        this.hoverState = null, this.$element = null, this.inState = null, this.init("tooltip", a, b);
    };
    c.VERSION = "3.3.7", c.TRANSITION_DURATION = 150, c.DEFAULTS = {
        animation: !0,
        placement: "top",
        selector: !1,
        template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
        trigger: "hover focus",
        title: "",
        delay: 0,
        html: !1,
        container: !1,
        viewport: {
            selector: "body",
            padding: 0
        }
    }, c.prototype.init = function(b, c, d) {
        if (this.enabled = !0, this.type = b, this.$element = a(c), this.options = this.getOptions(d), 
        this.$viewport = this.options.viewport && a(a.isFunction(this.options.viewport) ? this.options.viewport.call(this, this.$element) : this.options.viewport.selector || this.options.viewport), 
        this.inState = {
            click: !1,
            hover: !1,
            focus: !1
        }, this.$element[0] instanceof document.constructor && !this.options.selector) throw new Error("`selector` option must be specified when initializing " + this.type + " on the window.document object!");
        for (var e = this.options.trigger.split(" "), f = e.length; f--; ) {
            var g = e[f];
            if ("click" == g) this.$element.on("click." + this.type, this.options.selector, a.proxy(this.toggle, this)); else if ("manual" != g) {
                var h = "hover" == g ? "mouseenter" : "focusin", i = "hover" == g ? "mouseleave" : "focusout";
                this.$element.on(h + "." + this.type, this.options.selector, a.proxy(this.enter, this)), 
                this.$element.on(i + "." + this.type, this.options.selector, a.proxy(this.leave, this));
            }
        }
        this.options.selector ? this._options = a.extend({}, this.options, {
            trigger: "manual",
            selector: ""
        }) : this.fixTitle();
    }, c.prototype.getDefaults = function() {
        return c.DEFAULTS;
    }, c.prototype.getOptions = function(b) {
        return b = a.extend({}, this.getDefaults(), this.$element.data(), b), b.delay && "number" == typeof b.delay && (b.delay = {
            show: b.delay,
            hide: b.delay
        }), b;
    }, c.prototype.getDelegateOptions = function() {
        var b = {}, c = this.getDefaults();
        return this._options && a.each(this._options, function(a, d) {
            c[a] != d && (b[a] = d);
        }), b;
    }, c.prototype.enter = function(b) {
        var c = b instanceof this.constructor ? b : a(b.currentTarget).data("bs." + this.type);
        return c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), 
        a(b.currentTarget).data("bs." + this.type, c)), b instanceof a.Event && (c.inState["focusin" == b.type ? "focus" : "hover"] = !0), 
        c.tip().hasClass("in") || "in" == c.hoverState ? void (c.hoverState = "in") : (clearTimeout(c.timeout), 
        c.hoverState = "in", c.options.delay && c.options.delay.show ? void (c.timeout = setTimeout(function() {
            "in" == c.hoverState && c.show();
        }, c.options.delay.show)) : c.show());
    }, c.prototype.isInStateTrue = function() {
        for (var a in this.inState) if (this.inState[a]) return !0;
        return !1;
    }, c.prototype.leave = function(b) {
        var c = b instanceof this.constructor ? b : a(b.currentTarget).data("bs." + this.type);
        if (c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), 
        a(b.currentTarget).data("bs." + this.type, c)), b instanceof a.Event && (c.inState["focusout" == b.type ? "focus" : "hover"] = !1), 
        !c.isInStateTrue()) return clearTimeout(c.timeout), c.hoverState = "out", c.options.delay && c.options.delay.hide ? void (c.timeout = setTimeout(function() {
            "out" == c.hoverState && c.hide();
        }, c.options.delay.hide)) : c.hide();
    }, c.prototype.show = function() {
        var b = a.Event("show.bs." + this.type);
        if (this.hasContent() && this.enabled) {
            this.$element.trigger(b);
            var d = a.contains(this.$element[0].ownerDocument.documentElement, this.$element[0]);
            if (b.isDefaultPrevented() || !d) return;
            var e = this, f = this.tip(), g = this.getUID(this.type);
            this.setContent(), f.attr("id", g), this.$element.attr("aria-describedby", g), this.options.animation && f.addClass("fade");
            var h = "function" == typeof this.options.placement ? this.options.placement.call(this, f[0], this.$element[0]) : this.options.placement, i = /\s?auto?\s?/i, j = i.test(h);
            j && (h = h.replace(i, "") || "top"), f.detach().css({
                top: 0,
                left: 0,
                display: "block"
            }).addClass(h).data("bs." + this.type, this), this.options.container ? f.appendTo(this.options.container) : f.insertAfter(this.$element), 
            this.$element.trigger("inserted.bs." + this.type);
            var k = this.getPosition(), l = f[0].offsetWidth, m = f[0].offsetHeight;
            if (j) {
                var n = h, o = this.getPosition(this.$viewport);
                h = "bottom" == h && k.bottom + m > o.bottom ? "top" : "top" == h && k.top - m < o.top ? "bottom" : "right" == h && k.right + l > o.width ? "left" : "left" == h && k.left - l < o.left ? "right" : h, 
                f.removeClass(n).addClass(h);
            }
            var p = this.getCalculatedOffset(h, k, l, m);
            this.applyPlacement(p, h);
            var q = function() {
                var a = e.hoverState;
                e.$element.trigger("shown.bs." + e.type), e.hoverState = null, "out" == a && e.leave(e);
            };
            a.support.transition && this.$tip.hasClass("fade") ? f.one("bsTransitionEnd", q).emulateTransitionEnd(c.TRANSITION_DURATION) : q();
        }
    }, c.prototype.applyPlacement = function(b, c) {
        var d = this.tip(), e = d[0].offsetWidth, f = d[0].offsetHeight, g = parseInt(d.css("margin-top"), 10), h = parseInt(d.css("margin-left"), 10);
        isNaN(g) && (g = 0), isNaN(h) && (h = 0), b.top += g, b.left += h, a.offset.setOffset(d[0], a.extend({
            using: function(a) {
                d.css({
                    top: Math.round(a.top),
                    left: Math.round(a.left)
                });
            }
        }, b), 0), d.addClass("in");
        var i = d[0].offsetWidth, j = d[0].offsetHeight;
        "top" == c && j != f && (b.top = b.top + f - j);
        var k = this.getViewportAdjustedDelta(c, b, i, j);
        k.left ? b.left += k.left : b.top += k.top;
        var l = /top|bottom/.test(c), m = l ? 2 * k.left - e + i : 2 * k.top - f + j, n = l ? "offsetWidth" : "offsetHeight";
        d.offset(b), this.replaceArrow(m, d[0][n], l);
    }, c.prototype.replaceArrow = function(a, b, c) {
        this.arrow().css(c ? "left" : "top", 50 * (1 - a / b) + "%").css(c ? "top" : "left", "");
    }, c.prototype.setContent = function() {
        var a = this.tip(), b = this.getTitle();
        a.find(".tooltip-inner")[this.options.html ? "html" : "text"](b), a.removeClass("fade in top bottom left right");
    }, c.prototype.hide = function(b) {
        function d() {
            "in" != e.hoverState && f.detach(), e.$element && e.$element.removeAttr("aria-describedby").trigger("hidden.bs." + e.type), 
            b && b();
        }
        var e = this, f = a(this.$tip), g = a.Event("hide.bs." + this.type);
        if (this.$element.trigger(g), !g.isDefaultPrevented()) return f.removeClass("in"), 
        a.support.transition && f.hasClass("fade") ? f.one("bsTransitionEnd", d).emulateTransitionEnd(c.TRANSITION_DURATION) : d(), 
        this.hoverState = null, this;
    }, c.prototype.fixTitle = function() {
        var a = this.$element;
        (a.attr("title") || "string" != typeof a.attr("data-original-title")) && a.attr("data-original-title", a.attr("title") || "").attr("title", "");
    }, c.prototype.hasContent = function() {
        return this.getTitle();
    }, c.prototype.getPosition = function(b) {
        b = b || this.$element;
        var c = b[0], d = "BODY" == c.tagName, e = c.getBoundingClientRect();
        null == e.width && (e = a.extend({}, e, {
            width: e.right - e.left,
            height: e.bottom - e.top
        }));
        var f = window.SVGElement && c instanceof window.SVGElement, g = d ? {
            top: 0,
            left: 0
        } : f ? null : b.offset(), h = {
            scroll: d ? document.documentElement.scrollTop || document.body.scrollTop : b.scrollTop()
        }, i = d ? {
            width: a(window).width(),
            height: a(window).height()
        } : null;
        return a.extend({}, e, h, i, g);
    }, c.prototype.getCalculatedOffset = function(a, b, c, d) {
        return "bottom" == a ? {
            top: b.top + b.height,
            left: b.left + b.width / 2 - c / 2
        } : "top" == a ? {
            top: b.top - d,
            left: b.left + b.width / 2 - c / 2
        } : "left" == a ? {
            top: b.top + b.height / 2 - d / 2,
            left: b.left - c
        } : {
            top: b.top + b.height / 2 - d / 2,
            left: b.left + b.width
        };
    }, c.prototype.getViewportAdjustedDelta = function(a, b, c, d) {
        var e = {
            top: 0,
            left: 0
        };
        if (!this.$viewport) return e;
        var f = this.options.viewport && this.options.viewport.padding || 0, g = this.getPosition(this.$viewport);
        if (/right|left/.test(a)) {
            var h = b.top - f - g.scroll, i = b.top + f - g.scroll + d;
            h < g.top ? e.top = g.top - h : i > g.top + g.height && (e.top = g.top + g.height - i);
        } else {
            var j = b.left - f, k = b.left + f + c;
            j < g.left ? e.left = g.left - j : k > g.right && (e.left = g.left + g.width - k);
        }
        return e;
    }, c.prototype.getTitle = function() {
        var a, b = this.$element, c = this.options;
        return a = b.attr("data-original-title") || ("function" == typeof c.title ? c.title.call(b[0]) : c.title);
    }, c.prototype.getUID = function(a) {
        do a += ~~(1e6 * Math.random()); while (document.getElementById(a));
        return a;
    }, c.prototype.tip = function() {
        if (!this.$tip && (this.$tip = a(this.options.template), 1 != this.$tip.length)) throw new Error(this.type + " `template` option must consist of exactly 1 top-level element!");
        return this.$tip;
    }, c.prototype.arrow = function() {
        return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow");
    }, c.prototype.enable = function() {
        this.enabled = !0;
    }, c.prototype.disable = function() {
        this.enabled = !1;
    }, c.prototype.toggleEnabled = function() {
        this.enabled = !this.enabled;
    }, c.prototype.toggle = function(b) {
        var c = this;
        b && (c = a(b.currentTarget).data("bs." + this.type), c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), 
        a(b.currentTarget).data("bs." + this.type, c))), b ? (c.inState.click = !c.inState.click, 
        c.isInStateTrue() ? c.enter(c) : c.leave(c)) : c.tip().hasClass("in") ? c.leave(c) : c.enter(c);
    }, c.prototype.destroy = function() {
        var a = this;
        clearTimeout(this.timeout), this.hide(function() {
            a.$element.off("." + a.type).removeData("bs." + a.type), a.$tip && a.$tip.detach(), 
            a.$tip = null, a.$arrow = null, a.$viewport = null, a.$element = null;
        });
    };
    var d = a.fn.tooltip;
    a.fn.tooltip = b, a.fn.tooltip.Constructor = c, a.fn.tooltip.noConflict = function() {
        return a.fn.tooltip = d, this;
    };
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.popover"), f = "object" == typeof b && b;
            !e && /destroy|hide/.test(b) || (e || d.data("bs.popover", e = new c(this, f)), 
            "string" == typeof b && e[b]());
        });
    }
    var c = function(a, b) {
        this.init("popover", a, b);
    };
    if (!a.fn.tooltip) throw new Error("Popover requires tooltip.js");
    c.VERSION = "3.3.7", c.DEFAULTS = a.extend({}, a.fn.tooltip.Constructor.DEFAULTS, {
        placement: "right",
        trigger: "click",
        content: "",
        template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    }), c.prototype = a.extend({}, a.fn.tooltip.Constructor.prototype), c.prototype.constructor = c, 
    c.prototype.getDefaults = function() {
        return c.DEFAULTS;
    }, c.prototype.setContent = function() {
        var a = this.tip(), b = this.getTitle(), c = this.getContent();
        a.find(".popover-title")[this.options.html ? "html" : "text"](b), a.find(".popover-content").children().detach().end()[this.options.html ? "string" == typeof c ? "html" : "append" : "text"](c), 
        a.removeClass("fade top bottom left right in"), a.find(".popover-title").html() || a.find(".popover-title").hide();
    }, c.prototype.hasContent = function() {
        return this.getTitle() || this.getContent();
    }, c.prototype.getContent = function() {
        var a = this.$element, b = this.options;
        return a.attr("data-content") || ("function" == typeof b.content ? b.content.call(a[0]) : b.content);
    }, c.prototype.arrow = function() {
        return this.$arrow = this.$arrow || this.tip().find(".arrow");
    };
    var d = a.fn.popover;
    a.fn.popover = b, a.fn.popover.Constructor = c, a.fn.popover.noConflict = function() {
        return a.fn.popover = d, this;
    };
}(jQuery), +function(a) {
    "use strict";
    function b(c, d) {
        this.$body = a(document.body), this.$scrollElement = a(a(c).is(document.body) ? window : c), 
        this.options = a.extend({}, b.DEFAULTS, d), this.selector = (this.options.target || "") + " .nav li > a", 
        this.offsets = [], this.targets = [], this.activeTarget = null, this.scrollHeight = 0, 
        this.$scrollElement.on("scroll.bs.scrollspy", a.proxy(this.process, this)), this.refresh(), 
        this.process();
    }
    function c(c) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.scrollspy"), f = "object" == typeof c && c;
            e || d.data("bs.scrollspy", e = new b(this, f)), "string" == typeof c && e[c]();
        });
    }
    b.VERSION = "3.3.7", b.DEFAULTS = {
        offset: 10
    }, b.prototype.getScrollHeight = function() {
        return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight);
    }, b.prototype.refresh = function() {
        var b = this, c = "offset", d = 0;
        this.offsets = [], this.targets = [], this.scrollHeight = this.getScrollHeight(), 
        a.isWindow(this.$scrollElement[0]) || (c = "position", d = this.$scrollElement.scrollTop()), 
        this.$body.find(this.selector).map(function() {
            var b = a(this), e = b.data("target") || b.attr("href"), f = /^#./.test(e) && a(e);
            return f && f.length && f.is(":visible") && [ [ f[c]().top + d, e ] ] || null;
        }).sort(function(a, b) {
            return a[0] - b[0];
        }).each(function() {
            b.offsets.push(this[0]), b.targets.push(this[1]);
        });
    }, b.prototype.process = function() {
        var a, b = this.$scrollElement.scrollTop() + this.options.offset, c = this.getScrollHeight(), d = this.options.offset + c - this.$scrollElement.height(), e = this.offsets, f = this.targets, g = this.activeTarget;
        if (this.scrollHeight != c && this.refresh(), b >= d) return g != (a = f[f.length - 1]) && this.activate(a);
        if (g && b < e[0]) return this.activeTarget = null, this.clear();
        for (a = e.length; a--; ) g != f[a] && b >= e[a] && (void 0 === e[a + 1] || b < e[a + 1]) && this.activate(f[a]);
    }, b.prototype.activate = function(b) {
        this.activeTarget = b, this.clear();
        var c = this.selector + '[data-target="' + b + '"],' + this.selector + '[href="' + b + '"]', d = a(c).parents("li").addClass("active");
        d.parent(".dropdown-menu").length && (d = d.closest("li.dropdown").addClass("active")), 
        d.trigger("activate.bs.scrollspy");
    }, b.prototype.clear = function() {
        a(this.selector).parentsUntil(this.options.target, ".active").removeClass("active");
    };
    var d = a.fn.scrollspy;
    a.fn.scrollspy = c, a.fn.scrollspy.Constructor = b, a.fn.scrollspy.noConflict = function() {
        return a.fn.scrollspy = d, this;
    }, a(window).on("load.bs.scrollspy.data-api", function() {
        a('[data-spy="scroll"]').each(function() {
            var b = a(this);
            c.call(b, b.data());
        });
    });
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.tab");
            e || d.data("bs.tab", e = new c(this)), "string" == typeof b && e[b]();
        });
    }
    var c = function(b) {
        this.element = a(b);
    };
    c.VERSION = "3.3.7", c.TRANSITION_DURATION = 150, c.prototype.show = function() {
        var b = this.element, c = b.closest("ul:not(.dropdown-menu)"), d = b.data("target");
        if (d || (d = b.attr("href"), d = d && d.replace(/.*(?=#[^\s]*$)/, "")), !b.parent("li").hasClass("active")) {
            var e = c.find(".active:last a"), f = a.Event("hide.bs.tab", {
                relatedTarget: b[0]
            }), g = a.Event("show.bs.tab", {
                relatedTarget: e[0]
            });
            if (e.trigger(f), b.trigger(g), !g.isDefaultPrevented() && !f.isDefaultPrevented()) {
                var h = a(d);
                this.activate(b.closest("li"), c), this.activate(h, h.parent(), function() {
                    e.trigger({
                        type: "hidden.bs.tab",
                        relatedTarget: b[0]
                    }), b.trigger({
                        type: "shown.bs.tab",
                        relatedTarget: e[0]
                    });
                });
            }
        }
    }, c.prototype.activate = function(b, d, e) {
        function f() {
            g.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !1), 
            b.addClass("active").find('[data-toggle="tab"]').attr("aria-expanded", !0), h ? (b[0].offsetWidth, 
            b.addClass("in")) : b.removeClass("fade"), b.parent(".dropdown-menu").length && b.closest("li.dropdown").addClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !0), 
            e && e();
        }
        var g = d.find("> .active"), h = e && a.support.transition && (g.length && g.hasClass("fade") || !!d.find("> .fade").length);
        g.length && h ? g.one("bsTransitionEnd", f).emulateTransitionEnd(c.TRANSITION_DURATION) : f(), 
        g.removeClass("in");
    };
    var d = a.fn.tab;
    a.fn.tab = b, a.fn.tab.Constructor = c, a.fn.tab.noConflict = function() {
        return a.fn.tab = d, this;
    };
    var e = function(c) {
        c.preventDefault(), b.call(a(this), "show");
    };
    a(document).on("click.bs.tab.data-api", '[data-toggle="tab"]', e).on("click.bs.tab.data-api", '[data-toggle="pill"]', e);
}(jQuery), +function(a) {
    "use strict";
    function b(b) {
        return this.each(function() {
            var d = a(this), e = d.data("bs.affix"), f = "object" == typeof b && b;
            e || d.data("bs.affix", e = new c(this, f)), "string" == typeof b && e[b]();
        });
    }
    var c = function(b, d) {
        this.options = a.extend({}, c.DEFAULTS, d), this.$target = a(this.options.target).on("scroll.bs.affix.data-api", a.proxy(this.checkPosition, this)).on("click.bs.affix.data-api", a.proxy(this.checkPositionWithEventLoop, this)), 
        this.$element = a(b), this.affixed = null, this.unpin = null, this.pinnedOffset = null, 
        this.checkPosition();
    };
    c.VERSION = "3.3.7", c.RESET = "affix affix-top affix-bottom", c.DEFAULTS = {
        offset: 0,
        target: window
    }, c.prototype.getState = function(a, b, c, d) {
        var e = this.$target.scrollTop(), f = this.$element.offset(), g = this.$target.height();
        if (null != c && "top" == this.affixed) return e < c && "top";
        if ("bottom" == this.affixed) return null != c ? !(e + this.unpin <= f.top) && "bottom" : !(e + g <= a - d) && "bottom";
        var h = null == this.affixed, i = h ? e : f.top, j = h ? g : b;
        return null != c && e <= c ? "top" : null != d && i + j >= a - d && "bottom";
    }, c.prototype.getPinnedOffset = function() {
        if (this.pinnedOffset) return this.pinnedOffset;
        this.$element.removeClass(c.RESET).addClass("affix");
        var a = this.$target.scrollTop(), b = this.$element.offset();
        return this.pinnedOffset = b.top - a;
    }, c.prototype.checkPositionWithEventLoop = function() {
        setTimeout(a.proxy(this.checkPosition, this), 1);
    }, c.prototype.checkPosition = function() {
        if (this.$element.is(":visible")) {
            var b = this.$element.height(), d = this.options.offset, e = d.top, f = d.bottom, g = Math.max(a(document).height(), a(document.body).height());
            "object" != typeof d && (f = e = d), "function" == typeof e && (e = d.top(this.$element)), 
            "function" == typeof f && (f = d.bottom(this.$element));
            var h = this.getState(g, b, e, f);
            if (this.affixed != h) {
                null != this.unpin && this.$element.css("top", "");
                var i = "affix" + (h ? "-" + h : ""), j = a.Event(i + ".bs.affix");
                if (this.$element.trigger(j), j.isDefaultPrevented()) return;
                this.affixed = h, this.unpin = "bottom" == h ? this.getPinnedOffset() : null, this.$element.removeClass(c.RESET).addClass(i).trigger(i.replace("affix", "affixed") + ".bs.affix");
            }
            "bottom" == h && this.$element.offset({
                top: g - b - f
            });
        }
    };
    var d = a.fn.affix;
    a.fn.affix = b, a.fn.affix.Constructor = c, a.fn.affix.noConflict = function() {
        return a.fn.affix = d, this;
    }, a(window).on("load", function() {
        a('[data-spy="affix"]').each(function() {
            var c = a(this), d = c.data();
            d.offset = d.offset || {}, null != d.offsetBottom && (d.offset.bottom = d.offsetBottom), 
            null != d.offsetTop && (d.offset.top = d.offsetTop), b.call(c, d);
        });
    });
}(jQuery);

/**
 * Swiper 3.2.7
 * Most modern mobile touch slider and framework with hardware accelerated transitions
 * 
 * http://www.idangero.us/swiper/
 * 
 * Copyright 2015, Vladimir Kharlampidi
 * The iDangero.us
 * http://www.idangero.us/
 * 
 * Licensed under MIT
 * 
 * Released on: December 7, 2015
 */
!function() {
    "use strict";
    function e(e) {
        e.fn.swiper = function(a) {
            var r;
            return e(this).each(function() {
                var e = new t(this, a);
                r || (r = e);
            }), r;
        };
    }
    var a, t = function(e, s) {
        function i() {
            return "horizontal" === T.params.direction;
        }
        function n(e) {
            return Math.floor(e);
        }
        function o() {
            T.autoplayTimeoutId = setTimeout(function() {
                T.params.loop ? (T.fixLoop(), T._slideNext()) : T.isEnd ? s.autoplayStopOnLast ? T.stopAutoplay() : T._slideTo(0) : T._slideNext();
            }, T.params.autoplay);
        }
        function l(e, t) {
            var r = a(e.target);
            if (!r.is(t)) if ("string" == typeof t) r = r.parents(t); else if (t.nodeType) {
                var s;
                return r.parents().each(function(e, a) {
                    a === t && (s = t);
                }), s ? t : void 0;
            }
            if (0 !== r.length) return r[0];
        }
        function d(e, a) {
            a = a || {};
            var t = window.MutationObserver || window.WebkitMutationObserver, r = new t(function(e) {
                e.forEach(function(e) {
                    T.onResize(!0), T.emit("onObserverUpdate", T, e);
                });
            });
            r.observe(e, {
                attributes: "undefined" == typeof a.attributes ? !0 : a.attributes,
                childList: "undefined" == typeof a.childList ? !0 : a.childList,
                characterData: "undefined" == typeof a.characterData ? !0 : a.characterData
            }), T.observers.push(r);
        }
        function p(e) {
            e.originalEvent && (e = e.originalEvent);
            var a = e.keyCode || e.charCode;
            if (!T.params.allowSwipeToNext && (i() && 39 === a || !i() && 40 === a)) return !1;
            if (!T.params.allowSwipeToPrev && (i() && 37 === a || !i() && 38 === a)) return !1;
            if (!(e.shiftKey || e.altKey || e.ctrlKey || e.metaKey || document.activeElement && document.activeElement.nodeName && ("input" === document.activeElement.nodeName.toLowerCase() || "textarea" === document.activeElement.nodeName.toLowerCase()))) {
                if (37 === a || 39 === a || 38 === a || 40 === a) {
                    var t = !1;
                    if (T.container.parents(".swiper-slide").length > 0 && 0 === T.container.parents(".swiper-slide-active").length) return;
                    var r = {
                        left: window.pageXOffset,
                        top: window.pageYOffset
                    }, s = window.innerWidth, n = window.innerHeight, o = T.container.offset();
                    T.rtl && (o.left = o.left - T.container[0].scrollLeft);
                    for (var l = [ [ o.left, o.top ], [ o.left + T.width, o.top ], [ o.left, o.top + T.height ], [ o.left + T.width, o.top + T.height ] ], d = 0; d < l.length; d++) {
                        var p = l[d];
                        p[0] >= r.left && p[0] <= r.left + s && p[1] >= r.top && p[1] <= r.top + n && (t = !0);
                    }
                    if (!t) return;
                }
                i() ? ((37 === a || 39 === a) && (e.preventDefault ? e.preventDefault() : e.returnValue = !1), 
                (39 === a && !T.rtl || 37 === a && T.rtl) && T.slideNext(), (37 === a && !T.rtl || 39 === a && T.rtl) && T.slidePrev()) : ((38 === a || 40 === a) && (e.preventDefault ? e.preventDefault() : e.returnValue = !1), 
                40 === a && T.slideNext(), 38 === a && T.slidePrev());
            }
        }
        function u(e) {
            e.originalEvent && (e = e.originalEvent);
            var a = T.mousewheel.event, t = 0, r = T.rtl ? -1 : 1;
            if (e.detail) t = -e.detail; else if ("mousewheel" === a) if (T.params.mousewheelForceToAxis) if (i()) {
                if (!(Math.abs(e.wheelDeltaX) > Math.abs(e.wheelDeltaY))) return;
                t = e.wheelDeltaX * r;
            } else {
                if (!(Math.abs(e.wheelDeltaY) > Math.abs(e.wheelDeltaX))) return;
                t = e.wheelDeltaY;
            } else t = Math.abs(e.wheelDeltaX) > Math.abs(e.wheelDeltaY) ? -e.wheelDeltaX * r : -e.wheelDeltaY; else if ("DOMMouseScroll" === a) t = -e.detail; else if ("wheel" === a) if (T.params.mousewheelForceToAxis) if (i()) {
                if (!(Math.abs(e.deltaX) > Math.abs(e.deltaY))) return;
                t = -e.deltaX * r;
            } else {
                if (!(Math.abs(e.deltaY) > Math.abs(e.deltaX))) return;
                t = -e.deltaY;
            } else t = Math.abs(e.deltaX) > Math.abs(e.deltaY) ? -e.deltaX * r : -e.deltaY;
            if (0 !== t) {
                if (T.params.mousewheelInvert && (t = -t), T.params.freeMode) {
                    var s = T.getWrapperTranslate() + t * T.params.mousewheelSensitivity, n = T.isBeginning, o = T.isEnd;
                    if (s >= T.minTranslate() && (s = T.minTranslate()), s <= T.maxTranslate() && (s = T.maxTranslate()), 
                    T.setWrapperTransition(0), T.setWrapperTranslate(s), T.updateProgress(), T.updateActiveIndex(), 
                    (!n && T.isBeginning || !o && T.isEnd) && T.updateClasses(), T.params.freeModeSticky && (clearTimeout(T.mousewheel.timeout), 
                    T.mousewheel.timeout = setTimeout(function() {
                        T.slideReset();
                    }, 300)), 0 === s || s === T.maxTranslate()) return;
                } else {
                    if (new window.Date().getTime() - T.mousewheel.lastScrollTime > 60) if (0 > t) if (T.isEnd && !T.params.loop || T.animating) {
                        if (T.params.mousewheelReleaseOnEdges) return !0;
                    } else T.slideNext(); else if (T.isBeginning && !T.params.loop || T.animating) {
                        if (T.params.mousewheelReleaseOnEdges) return !0;
                    } else T.slidePrev();
                    T.mousewheel.lastScrollTime = new window.Date().getTime();
                }
                return T.params.autoplay && T.stopAutoplay(), e.preventDefault ? e.preventDefault() : e.returnValue = !1, 
                !1;
            }
        }
        function c(e, t) {
            e = a(e);
            var r, s, n, o = T.rtl ? -1 : 1;
            r = e.attr("data-swiper-parallax") || "0", s = e.attr("data-swiper-parallax-x"), 
            n = e.attr("data-swiper-parallax-y"), s || n ? (s = s || "0", n = n || "0") : i() ? (s = r, 
            n = "0") : (n = r, s = "0"), s = s.indexOf("%") >= 0 ? parseInt(s, 10) * t * o + "%" : s * t * o + "px", 
            n = n.indexOf("%") >= 0 ? parseInt(n, 10) * t + "%" : n * t + "px", e.transform("translate3d(" + s + ", " + n + ",0px)");
        }
        function m(e) {
            return 0 !== e.indexOf("on") && (e = e[0] !== e[0].toUpperCase() ? "on" + e[0].toUpperCase() + e.substring(1) : "on" + e), 
            e;
        }
        if (!(this instanceof t)) return new t(e, s);
        var f = {
            direction: "horizontal",
            touchEventsTarget: "container",
            initialSlide: 0,
            speed: 300,
            autoplay: !1,
            autoplayDisableOnInteraction: !0,
            iOSEdgeSwipeDetection: !1,
            iOSEdgeSwipeThreshold: 20,
            freeMode: !1,
            freeModeMomentum: !0,
            freeModeMomentumRatio: 1,
            freeModeMomentumBounce: !0,
            freeModeMomentumBounceRatio: 1,
            freeModeSticky: !1,
            freeModeMinimumVelocity: .02,
            autoHeight: !1,
            setWrapperSize: !1,
            virtualTranslate: !1,
            effect: "slide",
            coverflow: {
                rotate: 50,
                stretch: 0,
                depth: 100,
                modifier: 1,
                slideShadows: !0
            },
            cube: {
                slideShadows: !0,
                shadow: !0,
                shadowOffset: 20,
                shadowScale: .94
            },
            fade: {
                crossFade: !1
            },
            parallax: !1,
            scrollbar: null,
            scrollbarHide: !0,
            scrollbarDraggable: !1,
            scrollbarSnapOnRelease: !1,
            keyboardControl: !1,
            mousewheelControl: !1,
            mousewheelReleaseOnEdges: !1,
            mousewheelInvert: !1,
            mousewheelForceToAxis: !1,
            mousewheelSensitivity: 1,
            hashnav: !1,
            breakpoints: void 0,
            spaceBetween: 0,
            slidesPerView: 1,
            slidesPerColumn: 1,
            slidesPerColumnFill: "column",
            slidesPerGroup: 1,
            centeredSlides: !1,
            slidesOffsetBefore: 0,
            slidesOffsetAfter: 0,
            roundLengths: !1,
            touchRatio: 1,
            touchAngle: 45,
            simulateTouch: !0,
            shortSwipes: !0,
            longSwipes: !0,
            longSwipesRatio: .5,
            longSwipesMs: 300,
            followFinger: !0,
            onlyExternal: !1,
            threshold: 0,
            touchMoveStopPropagation: !0,
            pagination: null,
            paginationElement: "span",
            paginationClickable: !1,
            paginationHide: !1,
            paginationBulletRender: null,
            resistance: !0,
            resistanceRatio: .85,
            nextButton: null,
            prevButton: null,
            watchSlidesProgress: !1,
            watchSlidesVisibility: !1,
            grabCursor: !1,
            preventClicks: !0,
            preventClicksPropagation: !0,
            slideToClickedSlide: !1,
            lazyLoading: !1,
            lazyLoadingInPrevNext: !1,
            lazyLoadingOnTransitionStart: !1,
            preloadImages: !0,
            updateOnImagesReady: !0,
            loop: !1,
            loopAdditionalSlides: 0,
            loopedSlides: null,
            control: void 0,
            controlInverse: !1,
            controlBy: "slide",
            allowSwipeToPrev: !0,
            allowSwipeToNext: !0,
            swipeHandler: null,
            noSwiping: !0,
            noSwipingClass: "swiper-no-swiping",
            slideClass: "swiper-slide",
            slideActiveClass: "swiper-slide-active",
            slideVisibleClass: "swiper-slide-visible",
            slideDuplicateClass: "swiper-slide-duplicate",
            slideNextClass: "swiper-slide-next",
            slidePrevClass: "swiper-slide-prev",
            wrapperClass: "swiper-wrapper",
            bulletClass: "swiper-pagination-bullet",
            bulletActiveClass: "swiper-pagination-bullet-active",
            buttonDisabledClass: "swiper-button-disabled",
            paginationHiddenClass: "swiper-pagination-hidden",
            observer: !1,
            observeParents: !1,
            a11y: !1,
            prevSlideMessage: "Previous slide",
            nextSlideMessage: "Next slide",
            firstSlideMessage: "This is the first slide",
            lastSlideMessage: "This is the last slide",
            paginationBulletMessage: "Go to slide {{index}}",
            runCallbacksOnInit: !0
        }, h = s && s.virtualTranslate;
        s = s || {};
        var g = {};
        for (var v in s) if ("object" != typeof s[v] || (s[v].nodeType || s[v] === window || s[v] === document || "undefined" != typeof r && s[v] instanceof r || "undefined" != typeof jQuery && s[v] instanceof jQuery)) g[v] = s[v]; else {
            g[v] = {};
            for (var w in s[v]) g[v][w] = s[v][w];
        }
        for (var y in f) if ("undefined" == typeof s[y]) s[y] = f[y]; else if ("object" == typeof s[y]) for (var b in f[y]) "undefined" == typeof s[y][b] && (s[y][b] = f[y][b]);
        var T = this;
        if (T.params = s, T.originalParams = g, T.classNames = [], "undefined" != typeof a && "undefined" != typeof r && (a = r), 
        ("undefined" != typeof a || (a = "undefined" == typeof r ? window.Dom7 || window.Zepto || window.jQuery : r)) && (T.$ = a, 
        T.currentBreakpoint = void 0, T.getActiveBreakpoint = function() {
            if (!T.params.breakpoints) return !1;
            var e, a = !1, t = [];
            for (e in T.params.breakpoints) T.params.breakpoints.hasOwnProperty(e) && t.push(e);
            t.sort(function(e, a) {
                return parseInt(e, 10) > parseInt(a, 10);
            });
            for (var r = 0; r < t.length; r++) e = t[r], e >= window.innerWidth && !a && (a = e);
            return a || "max";
        }, T.setBreakpoint = function() {
            var e = T.getActiveBreakpoint();
            if (e && T.currentBreakpoint !== e) {
                var a = e in T.params.breakpoints ? T.params.breakpoints[e] : T.originalParams;
                for (var t in a) T.params[t] = a[t];
                T.currentBreakpoint = e;
            }
        }, T.params.breakpoints && T.setBreakpoint(), T.container = a(e), 0 !== T.container.length)) {
            if (T.container.length > 1) return void T.container.each(function() {
                new t(this, s);
            });
            T.container[0].swiper = T, T.container.data("swiper", T), T.classNames.push("swiper-container-" + T.params.direction), 
            T.params.freeMode && T.classNames.push("swiper-container-free-mode"), T.support.flexbox || (T.classNames.push("swiper-container-no-flexbox"), 
            T.params.slidesPerColumn = 1), T.params.autoHeight && T.classNames.push("swiper-container-autoheight"), 
            (T.params.parallax || T.params.watchSlidesVisibility) && (T.params.watchSlidesProgress = !0), 
            [ "cube", "coverflow" ].indexOf(T.params.effect) >= 0 && (T.support.transforms3d ? (T.params.watchSlidesProgress = !0, 
            T.classNames.push("swiper-container-3d")) : T.params.effect = "slide"), "slide" !== T.params.effect && T.classNames.push("swiper-container-" + T.params.effect), 
            "cube" === T.params.effect && (T.params.resistanceRatio = 0, T.params.slidesPerView = 1, 
            T.params.slidesPerColumn = 1, T.params.slidesPerGroup = 1, T.params.centeredSlides = !1, 
            T.params.spaceBetween = 0, T.params.virtualTranslate = !0, T.params.setWrapperSize = !1), 
            "fade" === T.params.effect && (T.params.slidesPerView = 1, T.params.slidesPerColumn = 1, 
            T.params.slidesPerGroup = 1, T.params.watchSlidesProgress = !0, T.params.spaceBetween = 0, 
            "undefined" == typeof h && (T.params.virtualTranslate = !0)), T.params.grabCursor && T.support.touch && (T.params.grabCursor = !1), 
            T.wrapper = T.container.children("." + T.params.wrapperClass), T.params.pagination && (T.paginationContainer = a(T.params.pagination), 
            T.params.paginationClickable && T.paginationContainer.addClass("swiper-pagination-clickable")), 
            T.rtl = i() && ("rtl" === T.container[0].dir.toLowerCase() || "rtl" === T.container.css("direction")), 
            T.rtl && T.classNames.push("swiper-container-rtl"), T.rtl && (T.wrongRTL = "-webkit-box" === T.wrapper.css("display")), 
            T.params.slidesPerColumn > 1 && T.classNames.push("swiper-container-multirow"), 
            T.device.android && T.classNames.push("swiper-container-android"), T.container.addClass(T.classNames.join(" ")), 
            T.translate = 0, T.progress = 0, T.velocity = 0, T.lockSwipeToNext = function() {
                T.params.allowSwipeToNext = !1;
            }, T.lockSwipeToPrev = function() {
                T.params.allowSwipeToPrev = !1;
            }, T.lockSwipes = function() {
                T.params.allowSwipeToNext = T.params.allowSwipeToPrev = !1;
            }, T.unlockSwipeToNext = function() {
                T.params.allowSwipeToNext = !0;
            }, T.unlockSwipeToPrev = function() {
                T.params.allowSwipeToPrev = !0;
            }, T.unlockSwipes = function() {
                T.params.allowSwipeToNext = T.params.allowSwipeToPrev = !0;
            }, T.params.grabCursor && (T.container[0].style.cursor = "move", T.container[0].style.cursor = "-webkit-grab", 
            T.container[0].style.cursor = "-moz-grab", T.container[0].style.cursor = "grab"), 
            T.imagesToLoad = [], T.imagesLoaded = 0, T.loadImage = function(e, a, t, r, s) {
                function i() {
                    s && s();
                }
                var n;
                e.complete && r ? i() : a ? (n = new window.Image(), n.onload = i, n.onerror = i, 
                t && (n.srcset = t), a && (n.src = a)) : i();
            }, T.preloadImages = function() {
                function e() {
                    "undefined" != typeof T && null !== T && (void 0 !== T.imagesLoaded && T.imagesLoaded++, 
                    T.imagesLoaded === T.imagesToLoad.length && (T.params.updateOnImagesReady && T.update(), 
                    T.emit("onImagesReady", T)));
                }
                T.imagesToLoad = T.container.find("img");
                for (var a = 0; a < T.imagesToLoad.length; a++) T.loadImage(T.imagesToLoad[a], T.imagesToLoad[a].currentSrc || T.imagesToLoad[a].getAttribute("src"), T.imagesToLoad[a].srcset || T.imagesToLoad[a].getAttribute("srcset"), !0, e);
            }, T.autoplayTimeoutId = void 0, T.autoplaying = !1, T.autoplayPaused = !1, T.startAutoplay = function() {
                return "undefined" != typeof T.autoplayTimeoutId ? !1 : T.params.autoplay ? T.autoplaying ? !1 : (T.autoplaying = !0, 
                T.emit("onAutoplayStart", T), void o()) : !1;
            }, T.stopAutoplay = function(e) {
                T.autoplayTimeoutId && (T.autoplayTimeoutId && clearTimeout(T.autoplayTimeoutId), 
                T.autoplaying = !1, T.autoplayTimeoutId = void 0, T.emit("onAutoplayStop", T));
            }, T.pauseAutoplay = function(e) {
                T.autoplayPaused || (T.autoplayTimeoutId && clearTimeout(T.autoplayTimeoutId), T.autoplayPaused = !0, 
                0 === e ? (T.autoplayPaused = !1, o()) : T.wrapper.transitionEnd(function() {
                    T && (T.autoplayPaused = !1, T.autoplaying ? o() : T.stopAutoplay());
                }));
            }, T.minTranslate = function() {
                return -T.snapGrid[0];
            }, T.maxTranslate = function() {
                return -T.snapGrid[T.snapGrid.length - 1];
            }, T.updateAutoHeight = function() {
                var e = T.slides.eq(T.activeIndex)[0].offsetHeight;
                e && T.wrapper.css("height", T.slides.eq(T.activeIndex)[0].offsetHeight + "px");
            }, T.updateContainerSize = function() {
                var e, a;
                e = "undefined" != typeof T.params.width ? T.params.width : T.container[0].clientWidth, 
                a = "undefined" != typeof T.params.height ? T.params.height : T.container[0].clientHeight, 
                0 === e && i() || 0 === a && !i() || (e = e - parseInt(T.container.css("padding-left"), 10) - parseInt(T.container.css("padding-right"), 10), 
                a = a - parseInt(T.container.css("padding-top"), 10) - parseInt(T.container.css("padding-bottom"), 10), 
                T.width = e, T.height = a, T.size = i() ? T.width : T.height);
            }, T.updateSlidesSize = function() {
                T.slides = T.wrapper.children("." + T.params.slideClass), T.snapGrid = [], T.slidesGrid = [], 
                T.slidesSizesGrid = [];
                var e, a = T.params.spaceBetween, t = -T.params.slidesOffsetBefore, r = 0, s = 0;
                "string" == typeof a && a.indexOf("%") >= 0 && (a = parseFloat(a.replace("%", "")) / 100 * T.size), 
                T.virtualSize = -a, T.rtl ? T.slides.css({
                    marginLeft: "",
                    marginTop: ""
                }) : T.slides.css({
                    marginRight: "",
                    marginBottom: ""
                });
                var o;
                T.params.slidesPerColumn > 1 && (o = Math.floor(T.slides.length / T.params.slidesPerColumn) === T.slides.length / T.params.slidesPerColumn ? T.slides.length : Math.ceil(T.slides.length / T.params.slidesPerColumn) * T.params.slidesPerColumn, 
                "auto" !== T.params.slidesPerView && "row" === T.params.slidesPerColumnFill && (o = Math.max(o, T.params.slidesPerView * T.params.slidesPerColumn)));
                var l, d = T.params.slidesPerColumn, p = o / d, u = p - (T.params.slidesPerColumn * p - T.slides.length);
                for (e = 0; e < T.slides.length; e++) {
                    l = 0;
                    var c = T.slides.eq(e);
                    if (T.params.slidesPerColumn > 1) {
                        var m, f, h;
                        "column" === T.params.slidesPerColumnFill ? (f = Math.floor(e / d), h = e - f * d, 
                        (f > u || f === u && h === d - 1) && ++h >= d && (h = 0, f++), m = f + h * o / d, 
                        c.css({
                            "-webkit-box-ordinal-group": m,
                            "-moz-box-ordinal-group": m,
                            "-ms-flex-order": m,
                            "-webkit-order": m,
                            order: m
                        })) : (h = Math.floor(e / p), f = e - h * p), c.css({
                            "margin-top": 0 !== h && T.params.spaceBetween && T.params.spaceBetween + "px"
                        }).attr("data-swiper-column", f).attr("data-swiper-row", h);
                    }
                    "none" !== c.css("display") && ("auto" === T.params.slidesPerView ? (l = i() ? c.outerWidth(!0) : c.outerHeight(!0), 
                    T.params.roundLengths && (l = n(l))) : (l = (T.size - (T.params.slidesPerView - 1) * a) / T.params.slidesPerView, 
                    T.params.roundLengths && (l = n(l)), i() ? T.slides[e].style.width = l + "px" : T.slides[e].style.height = l + "px"), 
                    T.slides[e].swiperSlideSize = l, T.slidesSizesGrid.push(l), T.params.centeredSlides ? (t = t + l / 2 + r / 2 + a, 
                    0 === e && (t = t - T.size / 2 - a), Math.abs(t) < .001 && (t = 0), s % T.params.slidesPerGroup === 0 && T.snapGrid.push(t), 
                    T.slidesGrid.push(t)) : (s % T.params.slidesPerGroup === 0 && T.snapGrid.push(t), 
                    T.slidesGrid.push(t), t = t + l + a), T.virtualSize += l + a, r = l, s++);
                }
                T.virtualSize = Math.max(T.virtualSize, T.size) + T.params.slidesOffsetAfter;
                var g;
                if (T.rtl && T.wrongRTL && ("slide" === T.params.effect || "coverflow" === T.params.effect) && T.wrapper.css({
                    width: T.virtualSize + T.params.spaceBetween + "px"
                }), (!T.support.flexbox || T.params.setWrapperSize) && (i() ? T.wrapper.css({
                    width: T.virtualSize + T.params.spaceBetween + "px"
                }) : T.wrapper.css({
                    height: T.virtualSize + T.params.spaceBetween + "px"
                })), T.params.slidesPerColumn > 1 && (T.virtualSize = (l + T.params.spaceBetween) * o, 
                T.virtualSize = Math.ceil(T.virtualSize / T.params.slidesPerColumn) - T.params.spaceBetween, 
                T.wrapper.css({
                    width: T.virtualSize + T.params.spaceBetween + "px"
                }), T.params.centeredSlides)) {
                    for (g = [], e = 0; e < T.snapGrid.length; e++) T.snapGrid[e] < T.virtualSize + T.snapGrid[0] && g.push(T.snapGrid[e]);
                    T.snapGrid = g;
                }
                if (!T.params.centeredSlides) {
                    for (g = [], e = 0; e < T.snapGrid.length; e++) T.snapGrid[e] <= T.virtualSize - T.size && g.push(T.snapGrid[e]);
                    T.snapGrid = g, Math.floor(T.virtualSize - T.size) > Math.floor(T.snapGrid[T.snapGrid.length - 1]) && T.snapGrid.push(T.virtualSize - T.size);
                }
                0 === T.snapGrid.length && (T.snapGrid = [ 0 ]), 0 !== T.params.spaceBetween && (i() ? T.rtl ? T.slides.css({
                    marginLeft: a + "px"
                }) : T.slides.css({
                    marginRight: a + "px"
                }) : T.slides.css({
                    marginBottom: a + "px"
                })), T.params.watchSlidesProgress && T.updateSlidesOffset();
            }, T.updateSlidesOffset = function() {
                for (var e = 0; e < T.slides.length; e++) T.slides[e].swiperSlideOffset = i() ? T.slides[e].offsetLeft : T.slides[e].offsetTop;
            }, T.updateSlidesProgress = function(e) {
                if ("undefined" == typeof e && (e = T.translate || 0), 0 !== T.slides.length) {
                    "undefined" == typeof T.slides[0].swiperSlideOffset && T.updateSlidesOffset();
                    var a = -e;
                    T.rtl && (a = e), T.slides.removeClass(T.params.slideVisibleClass);
                    for (var t = 0; t < T.slides.length; t++) {
                        var r = T.slides[t], s = (a - r.swiperSlideOffset) / (r.swiperSlideSize + T.params.spaceBetween);
                        if (T.params.watchSlidesVisibility) {
                            var i = -(a - r.swiperSlideOffset), n = i + T.slidesSizesGrid[t], o = i >= 0 && i < T.size || n > 0 && n <= T.size || 0 >= i && n >= T.size;
                            o && T.slides.eq(t).addClass(T.params.slideVisibleClass);
                        }
                        r.progress = T.rtl ? -s : s;
                    }
                }
            }, T.updateProgress = function(e) {
                "undefined" == typeof e && (e = T.translate || 0);
                var a = T.maxTranslate() - T.minTranslate(), t = T.isBeginning, r = T.isEnd;
                0 === a ? (T.progress = 0, T.isBeginning = T.isEnd = !0) : (T.progress = (e - T.minTranslate()) / a, 
                T.isBeginning = T.progress <= 0, T.isEnd = T.progress >= 1), T.isBeginning && !t && T.emit("onReachBeginning", T), 
                T.isEnd && !r && T.emit("onReachEnd", T), T.params.watchSlidesProgress && T.updateSlidesProgress(e), 
                T.emit("onProgress", T, T.progress);
            }, T.updateActiveIndex = function() {
                var e, a, t, r = T.rtl ? T.translate : -T.translate;
                for (a = 0; a < T.slidesGrid.length; a++) "undefined" != typeof T.slidesGrid[a + 1] ? r >= T.slidesGrid[a] && r < T.slidesGrid[a + 1] - (T.slidesGrid[a + 1] - T.slidesGrid[a]) / 2 ? e = a : r >= T.slidesGrid[a] && r < T.slidesGrid[a + 1] && (e = a + 1) : r >= T.slidesGrid[a] && (e = a);
                (0 > e || "undefined" == typeof e) && (e = 0), t = Math.floor(e / T.params.slidesPerGroup), 
                t >= T.snapGrid.length && (t = T.snapGrid.length - 1), e !== T.activeIndex && (T.snapIndex = t, 
                T.previousIndex = T.activeIndex, T.activeIndex = e, T.updateClasses());
            }, T.updateClasses = function() {
                T.slides.removeClass(T.params.slideActiveClass + " " + T.params.slideNextClass + " " + T.params.slidePrevClass);
                var e = T.slides.eq(T.activeIndex);
                if (e.addClass(T.params.slideActiveClass), e.next("." + T.params.slideClass).addClass(T.params.slideNextClass), 
                e.prev("." + T.params.slideClass).addClass(T.params.slidePrevClass), T.bullets && T.bullets.length > 0) {
                    T.bullets.removeClass(T.params.bulletActiveClass);
                    var t;
                    T.params.loop ? (t = Math.ceil(T.activeIndex - T.loopedSlides) / T.params.slidesPerGroup, 
                    t > T.slides.length - 1 - 2 * T.loopedSlides && (t -= T.slides.length - 2 * T.loopedSlides), 
                    t > T.bullets.length - 1 && (t -= T.bullets.length)) : t = "undefined" != typeof T.snapIndex ? T.snapIndex : T.activeIndex || 0, 
                    T.paginationContainer.length > 1 ? T.bullets.each(function() {
                        a(this).index() === t && a(this).addClass(T.params.bulletActiveClass);
                    }) : T.bullets.eq(t).addClass(T.params.bulletActiveClass);
                }
                T.params.loop || (T.params.prevButton && (T.isBeginning ? (a(T.params.prevButton).addClass(T.params.buttonDisabledClass), 
                T.params.a11y && T.a11y && T.a11y.disable(a(T.params.prevButton))) : (a(T.params.prevButton).removeClass(T.params.buttonDisabledClass), 
                T.params.a11y && T.a11y && T.a11y.enable(a(T.params.prevButton)))), T.params.nextButton && (T.isEnd ? (a(T.params.nextButton).addClass(T.params.buttonDisabledClass), 
                T.params.a11y && T.a11y && T.a11y.disable(a(T.params.nextButton))) : (a(T.params.nextButton).removeClass(T.params.buttonDisabledClass), 
                T.params.a11y && T.a11y && T.a11y.enable(a(T.params.nextButton)))));
            }, T.updatePagination = function() {
                if (T.params.pagination && T.paginationContainer && T.paginationContainer.length > 0) {
                    for (var e = "", a = T.params.loop ? Math.ceil((T.slides.length - 2 * T.loopedSlides) / T.params.slidesPerGroup) : T.snapGrid.length, t = 0; a > t; t++) e += T.params.paginationBulletRender ? T.params.paginationBulletRender(t, T.params.bulletClass) : "<" + T.params.paginationElement + ' class="' + T.params.bulletClass + '"></' + T.params.paginationElement + ">";
                    T.paginationContainer.html(e), T.bullets = T.paginationContainer.find("." + T.params.bulletClass), 
                    T.params.paginationClickable && T.params.a11y && T.a11y && T.a11y.initPagination();
                }
            }, T.update = function(e) {
                function a() {
                    r = Math.min(Math.max(T.translate, T.maxTranslate()), T.minTranslate()), T.setWrapperTranslate(r), 
                    T.updateActiveIndex(), T.updateClasses();
                }
                if (T.updateContainerSize(), T.updateSlidesSize(), T.updateProgress(), T.updatePagination(), 
                T.updateClasses(), T.params.scrollbar && T.scrollbar && T.scrollbar.set(), e) {
                    var t, r;
                    T.controller && T.controller.spline && (T.controller.spline = void 0), T.params.freeMode ? (a(), 
                    T.params.autoHeight && T.updateAutoHeight()) : (t = ("auto" === T.params.slidesPerView || T.params.slidesPerView > 1) && T.isEnd && !T.params.centeredSlides ? T.slideTo(T.slides.length - 1, 0, !1, !0) : T.slideTo(T.activeIndex, 0, !1, !0), 
                    t || a());
                } else T.params.autoHeight && T.updateAutoHeight();
            }, T.onResize = function(e) {
                T.params.breakpoints && T.setBreakpoint();
                var a = T.params.allowSwipeToPrev, t = T.params.allowSwipeToNext;
                if (T.params.allowSwipeToPrev = T.params.allowSwipeToNext = !0, T.updateContainerSize(), 
                T.updateSlidesSize(), ("auto" === T.params.slidesPerView || T.params.freeMode || e) && T.updatePagination(), 
                T.params.scrollbar && T.scrollbar && T.scrollbar.set(), T.controller && T.controller.spline && (T.controller.spline = void 0), 
                T.params.freeMode) {
                    var r = Math.min(Math.max(T.translate, T.maxTranslate()), T.minTranslate());
                    T.setWrapperTranslate(r), T.updateActiveIndex(), T.updateClasses(), T.params.autoHeight && T.updateAutoHeight();
                } else T.updateClasses(), ("auto" === T.params.slidesPerView || T.params.slidesPerView > 1) && T.isEnd && !T.params.centeredSlides ? T.slideTo(T.slides.length - 1, 0, !1, !0) : T.slideTo(T.activeIndex, 0, !1, !0);
                T.params.allowSwipeToPrev = a, T.params.allowSwipeToNext = t;
            };
            var x = [ "mousedown", "mousemove", "mouseup" ];
            window.navigator.pointerEnabled ? x = [ "pointerdown", "pointermove", "pointerup" ] : window.navigator.msPointerEnabled && (x = [ "MSPointerDown", "MSPointerMove", "MSPointerUp" ]), 
            T.touchEvents = {
                start: T.support.touch || !T.params.simulateTouch ? "touchstart" : x[0],
                move: T.support.touch || !T.params.simulateTouch ? "touchmove" : x[1],
                end: T.support.touch || !T.params.simulateTouch ? "touchend" : x[2]
            }, (window.navigator.pointerEnabled || window.navigator.msPointerEnabled) && ("container" === T.params.touchEventsTarget ? T.container : T.wrapper).addClass("swiper-wp8-" + T.params.direction), 
            T.initEvents = function(e) {
                var t = e ? "off" : "on", r = e ? "removeEventListener" : "addEventListener", i = "container" === T.params.touchEventsTarget ? T.container[0] : T.wrapper[0], n = T.support.touch ? i : document, o = T.params.nested ? !0 : !1;
                T.browser.ie ? (i[r](T.touchEvents.start, T.onTouchStart, !1), n[r](T.touchEvents.move, T.onTouchMove, o), 
                n[r](T.touchEvents.end, T.onTouchEnd, !1)) : (T.support.touch && (i[r](T.touchEvents.start, T.onTouchStart, !1), 
                i[r](T.touchEvents.move, T.onTouchMove, o), i[r](T.touchEvents.end, T.onTouchEnd, !1)), 
                !s.simulateTouch || T.device.ios || T.device.android || (i[r]("mousedown", T.onTouchStart, !1), 
                document[r]("mousemove", T.onTouchMove, o), document[r]("mouseup", T.onTouchEnd, !1))), 
                window[r]("resize", T.onResize), T.params.nextButton && (a(T.params.nextButton)[t]("click", T.onClickNext), 
                T.params.a11y && T.a11y && a(T.params.nextButton)[t]("keydown", T.a11y.onEnterKey)), 
                T.params.prevButton && (a(T.params.prevButton)[t]("click", T.onClickPrev), T.params.a11y && T.a11y && a(T.params.prevButton)[t]("keydown", T.a11y.onEnterKey)), 
                T.params.pagination && T.params.paginationClickable && (a(T.paginationContainer)[t]("click", "." + T.params.bulletClass, T.onClickIndex), 
                T.params.a11y && T.a11y && a(T.paginationContainer)[t]("keydown", "." + T.params.bulletClass, T.a11y.onEnterKey)), 
                (T.params.preventClicks || T.params.preventClicksPropagation) && i[r]("click", T.preventClicks, !0);
            }, T.attachEvents = function(e) {
                T.initEvents();
            }, T.detachEvents = function() {
                T.initEvents(!0);
            }, T.allowClick = !0, T.preventClicks = function(e) {
                T.allowClick || (T.params.preventClicks && e.preventDefault(), T.params.preventClicksPropagation && T.animating && (e.stopPropagation(), 
                e.stopImmediatePropagation()));
            }, T.onClickNext = function(e) {
                e.preventDefault(), (!T.isEnd || T.params.loop) && T.slideNext();
            }, T.onClickPrev = function(e) {
                e.preventDefault(), (!T.isBeginning || T.params.loop) && T.slidePrev();
            }, T.onClickIndex = function(e) {
                e.preventDefault();
                var t = a(this).index() * T.params.slidesPerGroup;
                T.params.loop && (t += T.loopedSlides), T.slideTo(t);
            }, T.updateClickedSlide = function(e) {
                var t = l(e, "." + T.params.slideClass), r = !1;
                if (t) for (var s = 0; s < T.slides.length; s++) T.slides[s] === t && (r = !0);
                if (!t || !r) return T.clickedSlide = void 0, void (T.clickedIndex = void 0);
                if (T.clickedSlide = t, T.clickedIndex = a(t).index(), T.params.slideToClickedSlide && void 0 !== T.clickedIndex && T.clickedIndex !== T.activeIndex) {
                    var i, n = T.clickedIndex;
                    if (T.params.loop) {
                        if (T.animating) return;
                        i = a(T.clickedSlide).attr("data-swiper-slide-index"), T.params.centeredSlides ? n < T.loopedSlides - T.params.slidesPerView / 2 || n > T.slides.length - T.loopedSlides + T.params.slidesPerView / 2 ? (T.fixLoop(), 
                        n = T.wrapper.children("." + T.params.slideClass + '[data-swiper-slide-index="' + i + '"]:not(.swiper-slide-duplicate)').eq(0).index(), 
                        setTimeout(function() {
                            T.slideTo(n);
                        }, 0)) : T.slideTo(n) : n > T.slides.length - T.params.slidesPerView ? (T.fixLoop(), 
                        n = T.wrapper.children("." + T.params.slideClass + '[data-swiper-slide-index="' + i + '"]:not(.swiper-slide-duplicate)').eq(0).index(), 
                        setTimeout(function() {
                            T.slideTo(n);
                        }, 0)) : T.slideTo(n);
                    } else T.slideTo(n);
                }
            };
            var S, C, M, E, P, k, z, I, L, D, B = "input, select, textarea, button", G = Date.now(), A = [];
            T.animating = !1, T.touches = {
                startX: 0,
                startY: 0,
                currentX: 0,
                currentY: 0,
                diff: 0
            };
            var O, N;
            if (T.onTouchStart = function(e) {
                if (e.originalEvent && (e = e.originalEvent), O = "touchstart" === e.type, O || !("which" in e) || 3 !== e.which) {
                    if (T.params.noSwiping && l(e, "." + T.params.noSwipingClass)) return void (T.allowClick = !0);
                    if (!T.params.swipeHandler || l(e, T.params.swipeHandler)) {
                        var t = T.touches.currentX = "touchstart" === e.type ? e.targetTouches[0].pageX : e.pageX, r = T.touches.currentY = "touchstart" === e.type ? e.targetTouches[0].pageY : e.pageY;
                        if (!(T.device.ios && T.params.iOSEdgeSwipeDetection && t <= T.params.iOSEdgeSwipeThreshold)) {
                            if (S = !0, C = !1, M = !0, P = void 0, N = void 0, T.touches.startX = t, T.touches.startY = r, 
                            E = Date.now(), T.allowClick = !0, T.updateContainerSize(), T.swipeDirection = void 0, 
                            T.params.threshold > 0 && (I = !1), "touchstart" !== e.type) {
                                var s = !0;
                                a(e.target).is(B) && (s = !1), document.activeElement && a(document.activeElement).is(B) && document.activeElement.blur(), 
                                s && e.preventDefault();
                            }
                            T.emit("onTouchStart", T, e);
                        }
                    }
                }
            }, T.onTouchMove = function(e) {
                if (e.originalEvent && (e = e.originalEvent), !(O && "mousemove" === e.type || e.preventedByNestedSwiper)) {
                    if (T.params.onlyExternal) return T.allowClick = !1, void (S && (T.touches.startX = T.touches.currentX = "touchmove" === e.type ? e.targetTouches[0].pageX : e.pageX, 
                    T.touches.startY = T.touches.currentY = "touchmove" === e.type ? e.targetTouches[0].pageY : e.pageY, 
                    E = Date.now()));
                    if (O && document.activeElement && e.target === document.activeElement && a(e.target).is(B)) return C = !0, 
                    void (T.allowClick = !1);
                    if (M && T.emit("onTouchMove", T, e), !(e.targetTouches && e.targetTouches.length > 1)) {
                        if (T.touches.currentX = "touchmove" === e.type ? e.targetTouches[0].pageX : e.pageX, 
                        T.touches.currentY = "touchmove" === e.type ? e.targetTouches[0].pageY : e.pageY, 
                        "undefined" == typeof P) {
                            var t = 180 * Math.atan2(Math.abs(T.touches.currentY - T.touches.startY), Math.abs(T.touches.currentX - T.touches.startX)) / Math.PI;
                            P = i() ? t > T.params.touchAngle : 90 - t > T.params.touchAngle;
                        }
                        if (P && T.emit("onTouchMoveOpposite", T, e), "undefined" == typeof N && T.browser.ieTouch && (T.touches.currentX !== T.touches.startX || T.touches.currentY !== T.touches.startY) && (N = !0), 
                        S) {
                            if (P) return void (S = !1);
                            if (N || !T.browser.ieTouch) {
                                T.allowClick = !1, T.emit("onSliderMove", T, e), e.preventDefault(), T.params.touchMoveStopPropagation && !T.params.nested && e.stopPropagation(), 
                                C || (s.loop && T.fixLoop(), z = T.getWrapperTranslate(), T.setWrapperTransition(0), 
                                T.animating && T.wrapper.trigger("webkitTransitionEnd transitionend oTransitionEnd MSTransitionEnd msTransitionEnd"), 
                                T.params.autoplay && T.autoplaying && (T.params.autoplayDisableOnInteraction ? T.stopAutoplay() : T.pauseAutoplay()), 
                                D = !1, T.params.grabCursor && (T.container[0].style.cursor = "move", T.container[0].style.cursor = "-webkit-grabbing", 
                                T.container[0].style.cursor = "-moz-grabbin", T.container[0].style.cursor = "grabbing")), 
                                C = !0;
                                var r = T.touches.diff = i() ? T.touches.currentX - T.touches.startX : T.touches.currentY - T.touches.startY;
                                r *= T.params.touchRatio, T.rtl && (r = -r), T.swipeDirection = r > 0 ? "prev" : "next", 
                                k = r + z;
                                var n = !0;
                                if (r > 0 && k > T.minTranslate() ? (n = !1, T.params.resistance && (k = T.minTranslate() - 1 + Math.pow(-T.minTranslate() + z + r, T.params.resistanceRatio))) : 0 > r && k < T.maxTranslate() && (n = !1, 
                                T.params.resistance && (k = T.maxTranslate() + 1 - Math.pow(T.maxTranslate() - z - r, T.params.resistanceRatio))), 
                                n && (e.preventedByNestedSwiper = !0), !T.params.allowSwipeToNext && "next" === T.swipeDirection && z > k && (k = z), 
                                !T.params.allowSwipeToPrev && "prev" === T.swipeDirection && k > z && (k = z), T.params.followFinger) {
                                    if (T.params.threshold > 0) {
                                        if (!(Math.abs(r) > T.params.threshold || I)) return void (k = z);
                                        if (!I) return I = !0, T.touches.startX = T.touches.currentX, T.touches.startY = T.touches.currentY, 
                                        k = z, void (T.touches.diff = i() ? T.touches.currentX - T.touches.startX : T.touches.currentY - T.touches.startY);
                                    }
                                    (T.params.freeMode || T.params.watchSlidesProgress) && T.updateActiveIndex(), T.params.freeMode && (0 === A.length && A.push({
                                        position: T.touches[i() ? "startX" : "startY"],
                                        time: E
                                    }), A.push({
                                        position: T.touches[i() ? "currentX" : "currentY"],
                                        time: new window.Date().getTime()
                                    })), T.updateProgress(k), T.setWrapperTranslate(k);
                                }
                            }
                        }
                    }
                }
            }, T.onTouchEnd = function(e) {
                if (e.originalEvent && (e = e.originalEvent), M && T.emit("onTouchEnd", T, e), M = !1, 
                S) {
                    T.params.grabCursor && C && S && (T.container[0].style.cursor = "move", T.container[0].style.cursor = "-webkit-grab", 
                    T.container[0].style.cursor = "-moz-grab", T.container[0].style.cursor = "grab");
                    var t = Date.now(), r = t - E;
                    if (T.allowClick && (T.updateClickedSlide(e), T.emit("onTap", T, e), 300 > r && t - G > 300 && (L && clearTimeout(L), 
                    L = setTimeout(function() {
                        T && (T.params.paginationHide && T.paginationContainer.length > 0 && !a(e.target).hasClass(T.params.bulletClass) && T.paginationContainer.toggleClass(T.params.paginationHiddenClass), 
                        T.emit("onClick", T, e));
                    }, 300)), 300 > r && 300 > t - G && (L && clearTimeout(L), T.emit("onDoubleTap", T, e))), 
                    G = Date.now(), setTimeout(function() {
                        T && (T.allowClick = !0);
                    }, 0), !S || !C || !T.swipeDirection || 0 === T.touches.diff || k === z) return void (S = C = !1);
                    S = C = !1;
                    var s;
                    if (s = T.params.followFinger ? T.rtl ? T.translate : -T.translate : -k, T.params.freeMode) {
                        if (s < -T.minTranslate()) return void T.slideTo(T.activeIndex);
                        if (s > -T.maxTranslate()) return void (T.slides.length < T.snapGrid.length ? T.slideTo(T.snapGrid.length - 1) : T.slideTo(T.slides.length - 1));
                        if (T.params.freeModeMomentum) {
                            if (A.length > 1) {
                                var i = A.pop(), n = A.pop(), o = i.position - n.position, l = i.time - n.time;
                                T.velocity = o / l, T.velocity = T.velocity / 2, Math.abs(T.velocity) < T.params.freeModeMinimumVelocity && (T.velocity = 0), 
                                (l > 150 || new window.Date().getTime() - i.time > 300) && (T.velocity = 0);
                            } else T.velocity = 0;
                            A.length = 0;
                            var d = 1e3 * T.params.freeModeMomentumRatio, p = T.velocity * d, u = T.translate + p;
                            T.rtl && (u = -u);
                            var c, m = !1, f = 20 * Math.abs(T.velocity) * T.params.freeModeMomentumBounceRatio;
                            if (u < T.maxTranslate()) T.params.freeModeMomentumBounce ? (u + T.maxTranslate() < -f && (u = T.maxTranslate() - f), 
                            c = T.maxTranslate(), m = !0, D = !0) : u = T.maxTranslate(); else if (u > T.minTranslate()) T.params.freeModeMomentumBounce ? (u - T.minTranslate() > f && (u = T.minTranslate() + f), 
                            c = T.minTranslate(), m = !0, D = !0) : u = T.minTranslate(); else if (T.params.freeModeSticky) {
                                var h, g = 0;
                                for (g = 0; g < T.snapGrid.length; g += 1) if (T.snapGrid[g] > -u) {
                                    h = g;
                                    break;
                                }
                                u = Math.abs(T.snapGrid[h] - u) < Math.abs(T.snapGrid[h - 1] - u) || "next" === T.swipeDirection ? T.snapGrid[h] : T.snapGrid[h - 1], 
                                T.rtl || (u = -u);
                            }
                            if (0 !== T.velocity) d = T.rtl ? Math.abs((-u - T.translate) / T.velocity) : Math.abs((u - T.translate) / T.velocity); else if (T.params.freeModeSticky) return void T.slideReset();
                            T.params.freeModeMomentumBounce && m ? (T.updateProgress(c), T.setWrapperTransition(d), 
                            T.setWrapperTranslate(u), T.onTransitionStart(), T.animating = !0, T.wrapper.transitionEnd(function() {
                                T && D && (T.emit("onMomentumBounce", T), T.setWrapperTransition(T.params.speed), 
                                T.setWrapperTranslate(c), T.wrapper.transitionEnd(function() {
                                    T && T.onTransitionEnd();
                                }));
                            })) : T.velocity ? (T.updateProgress(u), T.setWrapperTransition(d), T.setWrapperTranslate(u), 
                            T.onTransitionStart(), T.animating || (T.animating = !0, T.wrapper.transitionEnd(function() {
                                T && T.onTransitionEnd();
                            }))) : T.updateProgress(u), T.updateActiveIndex();
                        }
                        return void ((!T.params.freeModeMomentum || r >= T.params.longSwipesMs) && (T.updateProgress(), 
                        T.updateActiveIndex()));
                    }
                    var v, w = 0, y = T.slidesSizesGrid[0];
                    for (v = 0; v < T.slidesGrid.length; v += T.params.slidesPerGroup) "undefined" != typeof T.slidesGrid[v + T.params.slidesPerGroup] ? s >= T.slidesGrid[v] && s < T.slidesGrid[v + T.params.slidesPerGroup] && (w = v, 
                    y = T.slidesGrid[v + T.params.slidesPerGroup] - T.slidesGrid[v]) : s >= T.slidesGrid[v] && (w = v, 
                    y = T.slidesGrid[T.slidesGrid.length - 1] - T.slidesGrid[T.slidesGrid.length - 2]);
                    var b = (s - T.slidesGrid[w]) / y;
                    if (r > T.params.longSwipesMs) {
                        if (!T.params.longSwipes) return void T.slideTo(T.activeIndex);
                        "next" === T.swipeDirection && (b >= T.params.longSwipesRatio ? T.slideTo(w + T.params.slidesPerGroup) : T.slideTo(w)), 
                        "prev" === T.swipeDirection && (b > 1 - T.params.longSwipesRatio ? T.slideTo(w + T.params.slidesPerGroup) : T.slideTo(w));
                    } else {
                        if (!T.params.shortSwipes) return void T.slideTo(T.activeIndex);
                        "next" === T.swipeDirection && T.slideTo(w + T.params.slidesPerGroup), "prev" === T.swipeDirection && T.slideTo(w);
                    }
                }
            }, T._slideTo = function(e, a) {
                return T.slideTo(e, a, !0, !0);
            }, T.slideTo = function(e, a, t, r) {
                "undefined" == typeof t && (t = !0), "undefined" == typeof e && (e = 0), 0 > e && (e = 0), 
                T.snapIndex = Math.floor(e / T.params.slidesPerGroup), T.snapIndex >= T.snapGrid.length && (T.snapIndex = T.snapGrid.length - 1);
                var s = -T.snapGrid[T.snapIndex];
                T.params.autoplay && T.autoplaying && (r || !T.params.autoplayDisableOnInteraction ? T.pauseAutoplay(a) : T.stopAutoplay()), 
                T.updateProgress(s);
                for (var i = 0; i < T.slidesGrid.length; i++) -Math.floor(100 * s) >= Math.floor(100 * T.slidesGrid[i]) && (e = i);
                return !T.params.allowSwipeToNext && s < T.translate && s < T.minTranslate() ? !1 : !T.params.allowSwipeToPrev && s > T.translate && s > T.maxTranslate() && (T.activeIndex || 0) !== e ? !1 : ("undefined" == typeof a && (a = T.params.speed), 
                T.previousIndex = T.activeIndex || 0, T.activeIndex = e, T.rtl && -s === T.translate || !T.rtl && s === T.translate ? (T.params.autoHeight && T.updateAutoHeight(), 
                T.updateClasses(), "slide" !== T.params.effect && T.setWrapperTranslate(s), !1) : (T.updateClasses(), 
                T.onTransitionStart(t), 0 === a ? (T.setWrapperTranslate(s), T.setWrapperTransition(0), 
                T.onTransitionEnd(t)) : (T.setWrapperTranslate(s), T.setWrapperTransition(a), T.animating || (T.animating = !0, 
                T.wrapper.transitionEnd(function() {
                    T && T.onTransitionEnd(t);
                }))), !0));
            }, T.onTransitionStart = function(e) {
                "undefined" == typeof e && (e = !0), T.params.autoHeight && T.updateAutoHeight(), 
                T.lazy && T.lazy.onTransitionStart(), e && (T.emit("onTransitionStart", T), T.activeIndex !== T.previousIndex && (T.emit("onSlideChangeStart", T), 
                T.activeIndex > T.previousIndex ? T.emit("onSlideNextStart", T) : T.emit("onSlidePrevStart", T)));
            }, T.onTransitionEnd = function(e) {
                T.animating = !1, T.setWrapperTransition(0), "undefined" == typeof e && (e = !0), 
                T.lazy && T.lazy.onTransitionEnd(), e && (T.emit("onTransitionEnd", T), T.activeIndex !== T.previousIndex && (T.emit("onSlideChangeEnd", T), 
                T.activeIndex > T.previousIndex ? T.emit("onSlideNextEnd", T) : T.emit("onSlidePrevEnd", T))), 
                T.params.hashnav && T.hashnav && T.hashnav.setHash();
            }, T.slideNext = function(e, a, t) {
                if (T.params.loop) {
                    if (T.animating) return !1;
                    T.fixLoop();
                    T.container[0].clientLeft;
                    return T.slideTo(T.activeIndex + T.params.slidesPerGroup, a, e, t);
                }
                return T.slideTo(T.activeIndex + T.params.slidesPerGroup, a, e, t);
            }, T._slideNext = function(e) {
                return T.slideNext(!0, e, !0);
            }, T.slidePrev = function(e, a, t) {
                if (T.params.loop) {
                    if (T.animating) return !1;
                    T.fixLoop();
                    T.container[0].clientLeft;
                    return T.slideTo(T.activeIndex - 1, a, e, t);
                }
                return T.slideTo(T.activeIndex - 1, a, e, t);
            }, T._slidePrev = function(e) {
                return T.slidePrev(!0, e, !0);
            }, T.slideReset = function(e, a, t) {
                return T.slideTo(T.activeIndex, a, e);
            }, T.setWrapperTransition = function(e, a) {
                T.wrapper.transition(e), "slide" !== T.params.effect && T.effects[T.params.effect] && T.effects[T.params.effect].setTransition(e), 
                T.params.parallax && T.parallax && T.parallax.setTransition(e), T.params.scrollbar && T.scrollbar && T.scrollbar.setTransition(e), 
                T.params.control && T.controller && T.controller.setTransition(e, a), T.emit("onSetTransition", T, e);
            }, T.setWrapperTranslate = function(e, a, t) {
                var r = 0, s = 0, o = 0;
                i() ? r = T.rtl ? -e : e : s = e, T.params.roundLengths && (r = n(r), s = n(s)), 
                T.params.virtualTranslate || (T.support.transforms3d ? T.wrapper.transform("translate3d(" + r + "px, " + s + "px, " + o + "px)") : T.wrapper.transform("translate(" + r + "px, " + s + "px)")), 
                T.translate = i() ? r : s;
                var l, d = T.maxTranslate() - T.minTranslate();
                l = 0 === d ? 0 : (e - T.minTranslate()) / d, l !== T.progress && T.updateProgress(e), 
                a && T.updateActiveIndex(), "slide" !== T.params.effect && T.effects[T.params.effect] && T.effects[T.params.effect].setTranslate(T.translate), 
                T.params.parallax && T.parallax && T.parallax.setTranslate(T.translate), T.params.scrollbar && T.scrollbar && T.scrollbar.setTranslate(T.translate), 
                T.params.control && T.controller && T.controller.setTranslate(T.translate, t), T.emit("onSetTranslate", T, T.translate);
            }, T.getTranslate = function(e, a) {
                var t, r, s, i;
                return "undefined" == typeof a && (a = "x"), T.params.virtualTranslate ? T.rtl ? -T.translate : T.translate : (s = window.getComputedStyle(e, null), 
                window.WebKitCSSMatrix ? (r = s.transform || s.webkitTransform, r.split(",").length > 6 && (r = r.split(", ").map(function(e) {
                    return e.replace(",", ".");
                }).join(", ")), i = new window.WebKitCSSMatrix("none" === r ? "" : r)) : (i = s.MozTransform || s.OTransform || s.MsTransform || s.msTransform || s.transform || s.getPropertyValue("transform").replace("translate(", "matrix(1, 0, 0, 1,"), 
                t = i.toString().split(",")), "x" === a && (r = window.WebKitCSSMatrix ? i.m41 : 16 === t.length ? parseFloat(t[12]) : parseFloat(t[4])), 
                "y" === a && (r = window.WebKitCSSMatrix ? i.m42 : 16 === t.length ? parseFloat(t[13]) : parseFloat(t[5])), 
                T.rtl && r && (r = -r), r || 0);
            }, T.getWrapperTranslate = function(e) {
                return "undefined" == typeof e && (e = i() ? "x" : "y"), T.getTranslate(T.wrapper[0], e);
            }, T.observers = [], T.initObservers = function() {
                if (T.params.observeParents) for (var e = T.container.parents(), a = 0; a < e.length; a++) d(e[a]);
                d(T.container[0], {
                    childList: !1
                }), d(T.wrapper[0], {
                    attributes: !1
                });
            }, T.disconnectObservers = function() {
                for (var e = 0; e < T.observers.length; e++) T.observers[e].disconnect();
                T.observers = [];
            }, T.createLoop = function() {
                T.wrapper.children("." + T.params.slideClass + "." + T.params.slideDuplicateClass).remove();
                var e = T.wrapper.children("." + T.params.slideClass);
                "auto" !== T.params.slidesPerView || T.params.loopedSlides || (T.params.loopedSlides = e.length), 
                T.loopedSlides = parseInt(T.params.loopedSlides || T.params.slidesPerView, 10), 
                T.loopedSlides = T.loopedSlides + T.params.loopAdditionalSlides, T.loopedSlides > e.length && (T.loopedSlides = e.length);
                var t, r = [], s = [];
                for (e.each(function(t, i) {
                    var n = a(this);
                    t < T.loopedSlides && s.push(i), t < e.length && t >= e.length - T.loopedSlides && r.push(i), 
                    n.attr("data-swiper-slide-index", t);
                }), t = 0; t < s.length; t++) T.wrapper.append(a(s[t].cloneNode(!0)).addClass(T.params.slideDuplicateClass));
                for (t = r.length - 1; t >= 0; t--) T.wrapper.prepend(a(r[t].cloneNode(!0)).addClass(T.params.slideDuplicateClass));
            }, T.destroyLoop = function() {
                T.wrapper.children("." + T.params.slideClass + "." + T.params.slideDuplicateClass).remove(), 
                T.slides.removeAttr("data-swiper-slide-index");
            }, T.fixLoop = function() {
                var e;
                T.activeIndex < T.loopedSlides ? (e = T.slides.length - 3 * T.loopedSlides + T.activeIndex, 
                e += T.loopedSlides, T.slideTo(e, 0, !1, !0)) : ("auto" === T.params.slidesPerView && T.activeIndex >= 2 * T.loopedSlides || T.activeIndex > T.slides.length - 2 * T.params.slidesPerView) && (e = -T.slides.length + T.activeIndex + T.loopedSlides, 
                e += T.loopedSlides, T.slideTo(e, 0, !1, !0));
            }, T.appendSlide = function(e) {
                if (T.params.loop && T.destroyLoop(), "object" == typeof e && e.length) for (var a = 0; a < e.length; a++) e[a] && T.wrapper.append(e[a]); else T.wrapper.append(e);
                T.params.loop && T.createLoop(), T.params.observer && T.support.observer || T.update(!0);
            }, T.prependSlide = function(e) {
                T.params.loop && T.destroyLoop();
                var a = T.activeIndex + 1;
                if ("object" == typeof e && e.length) {
                    for (var t = 0; t < e.length; t++) e[t] && T.wrapper.prepend(e[t]);
                    a = T.activeIndex + e.length;
                } else T.wrapper.prepend(e);
                T.params.loop && T.createLoop(), T.params.observer && T.support.observer || T.update(!0), 
                T.slideTo(a, 0, !1);
            }, T.removeSlide = function(e) {
                T.params.loop && (T.destroyLoop(), T.slides = T.wrapper.children("." + T.params.slideClass));
                var a, t = T.activeIndex;
                if ("object" == typeof e && e.length) {
                    for (var r = 0; r < e.length; r++) a = e[r], T.slides[a] && T.slides.eq(a).remove(), 
                    t > a && t--;
                    t = Math.max(t, 0);
                } else a = e, T.slides[a] && T.slides.eq(a).remove(), t > a && t--, t = Math.max(t, 0);
                T.params.loop && T.createLoop(), T.params.observer && T.support.observer || T.update(!0), 
                T.params.loop ? T.slideTo(t + T.loopedSlides, 0, !1) : T.slideTo(t, 0, !1);
            }, T.removeAllSlides = function() {
                for (var e = [], a = 0; a < T.slides.length; a++) e.push(a);
                T.removeSlide(e);
            }, T.effects = {
                fade: {
                    setTranslate: function() {
                        for (var e = 0; e < T.slides.length; e++) {
                            var a = T.slides.eq(e), t = a[0].swiperSlideOffset, r = -t;
                            T.params.virtualTranslate || (r -= T.translate);
                            var s = 0;
                            i() || (s = r, r = 0);
                            var n = T.params.fade.crossFade ? Math.max(1 - Math.abs(a[0].progress), 0) : 1 + Math.min(Math.max(a[0].progress, -1), 0);
                            a.css({
                                opacity: n
                            }).transform("translate3d(" + r + "px, " + s + "px, 0px)");
                        }
                    },
                    setTransition: function(e) {
                        if (T.slides.transition(e), T.params.virtualTranslate && 0 !== e) {
                            var a = !1;
                            T.slides.transitionEnd(function() {
                                if (!a && T) {
                                    a = !0, T.animating = !1;
                                    for (var e = [ "webkitTransitionEnd", "transitionend", "oTransitionEnd", "MSTransitionEnd", "msTransitionEnd" ], t = 0; t < e.length; t++) T.wrapper.trigger(e[t]);
                                }
                            });
                        }
                    }
                },
                cube: {
                    setTranslate: function() {
                        var e, t = 0;
                        T.params.cube.shadow && (i() ? (e = T.wrapper.find(".swiper-cube-shadow"), 0 === e.length && (e = a('<div class="swiper-cube-shadow"></div>'), 
                        T.wrapper.append(e)), e.css({
                            height: T.width + "px"
                        })) : (e = T.container.find(".swiper-cube-shadow"), 0 === e.length && (e = a('<div class="swiper-cube-shadow"></div>'), 
                        T.container.append(e))));
                        for (var r = 0; r < T.slides.length; r++) {
                            var s = T.slides.eq(r), n = 90 * r, o = Math.floor(n / 360);
                            T.rtl && (n = -n, o = Math.floor(-n / 360));
                            var l = Math.max(Math.min(s[0].progress, 1), -1), d = 0, p = 0, u = 0;
                            r % 4 === 0 ? (d = 4 * -o * T.size, u = 0) : (r - 1) % 4 === 0 ? (d = 0, u = 4 * -o * T.size) : (r - 2) % 4 === 0 ? (d = T.size + 4 * o * T.size, 
                            u = T.size) : (r - 3) % 4 === 0 && (d = -T.size, u = 3 * T.size + 4 * T.size * o), 
                            T.rtl && (d = -d), i() || (p = d, d = 0);
                            var c = "rotateX(" + (i() ? 0 : -n) + "deg) rotateY(" + (i() ? n : 0) + "deg) translate3d(" + d + "px, " + p + "px, " + u + "px)";
                            if (1 >= l && l > -1 && (t = 90 * r + 90 * l, T.rtl && (t = 90 * -r - 90 * l)), 
                            s.transform(c), T.params.cube.slideShadows) {
                                var m = i() ? s.find(".swiper-slide-shadow-left") : s.find(".swiper-slide-shadow-top"), f = i() ? s.find(".swiper-slide-shadow-right") : s.find(".swiper-slide-shadow-bottom");
                                0 === m.length && (m = a('<div class="swiper-slide-shadow-' + (i() ? "left" : "top") + '"></div>'), 
                                s.append(m)), 0 === f.length && (f = a('<div class="swiper-slide-shadow-' + (i() ? "right" : "bottom") + '"></div>'), 
                                s.append(f));
                                s[0].progress;
                                m.length && (m[0].style.opacity = -s[0].progress), f.length && (f[0].style.opacity = s[0].progress);
                            }
                        }
                        if (T.wrapper.css({
                            "-webkit-transform-origin": "50% 50% -" + T.size / 2 + "px",
                            "-moz-transform-origin": "50% 50% -" + T.size / 2 + "px",
                            "-ms-transform-origin": "50% 50% -" + T.size / 2 + "px",
                            "transform-origin": "50% 50% -" + T.size / 2 + "px"
                        }), T.params.cube.shadow) if (i()) e.transform("translate3d(0px, " + (T.width / 2 + T.params.cube.shadowOffset) + "px, " + -T.width / 2 + "px) rotateX(90deg) rotateZ(0deg) scale(" + T.params.cube.shadowScale + ")"); else {
                            var h = Math.abs(t) - 90 * Math.floor(Math.abs(t) / 90), g = 1.5 - (Math.sin(2 * h * Math.PI / 360) / 2 + Math.cos(2 * h * Math.PI / 360) / 2), v = T.params.cube.shadowScale, w = T.params.cube.shadowScale / g, y = T.params.cube.shadowOffset;
                            e.transform("scale3d(" + v + ", 1, " + w + ") translate3d(0px, " + (T.height / 2 + y) + "px, " + -T.height / 2 / w + "px) rotateX(-90deg)");
                        }
                        var b = T.isSafari || T.isUiWebView ? -T.size / 2 : 0;
                        T.wrapper.transform("translate3d(0px,0," + b + "px) rotateX(" + (i() ? 0 : t) + "deg) rotateY(" + (i() ? -t : 0) + "deg)");
                    },
                    setTransition: function(e) {
                        T.slides.transition(e).find(".swiper-slide-shadow-top, .swiper-slide-shadow-right, .swiper-slide-shadow-bottom, .swiper-slide-shadow-left").transition(e), 
                        T.params.cube.shadow && !i() && T.container.find(".swiper-cube-shadow").transition(e);
                    }
                },
                coverflow: {
                    setTranslate: function() {
                        for (var e = T.translate, t = i() ? -e + T.width / 2 : -e + T.height / 2, r = i() ? T.params.coverflow.rotate : -T.params.coverflow.rotate, s = T.params.coverflow.depth, n = 0, o = T.slides.length; o > n; n++) {
                            var l = T.slides.eq(n), d = T.slidesSizesGrid[n], p = l[0].swiperSlideOffset, u = (t - p - d / 2) / d * T.params.coverflow.modifier, c = i() ? r * u : 0, m = i() ? 0 : r * u, f = -s * Math.abs(u), h = i() ? 0 : T.params.coverflow.stretch * u, g = i() ? T.params.coverflow.stretch * u : 0;
                            Math.abs(g) < .001 && (g = 0), Math.abs(h) < .001 && (h = 0), Math.abs(f) < .001 && (f = 0), 
                            Math.abs(c) < .001 && (c = 0), Math.abs(m) < .001 && (m = 0);
                            var v = "translate3d(" + g + "px," + h + "px," + f + "px)  rotateX(" + m + "deg) rotateY(" + c + "deg)";
                            if (l.transform(v), l[0].style.zIndex = -Math.abs(Math.round(u)) + 1, T.params.coverflow.slideShadows) {
                                var w = i() ? l.find(".swiper-slide-shadow-left") : l.find(".swiper-slide-shadow-top"), y = i() ? l.find(".swiper-slide-shadow-right") : l.find(".swiper-slide-shadow-bottom");
                                0 === w.length && (w = a('<div class="swiper-slide-shadow-' + (i() ? "left" : "top") + '"></div>'), 
                                l.append(w)), 0 === y.length && (y = a('<div class="swiper-slide-shadow-' + (i() ? "right" : "bottom") + '"></div>'), 
                                l.append(y)), w.length && (w[0].style.opacity = u > 0 ? u : 0), y.length && (y[0].style.opacity = -u > 0 ? -u : 0);
                            }
                        }
                        if (T.browser.ie) {
                            var b = T.wrapper[0].style;
                            b.perspectiveOrigin = t + "px 50%";
                        }
                    },
                    setTransition: function(e) {
                        T.slides.transition(e).find(".swiper-slide-shadow-top, .swiper-slide-shadow-right, .swiper-slide-shadow-bottom, .swiper-slide-shadow-left").transition(e);
                    }
                }
            }, T.lazy = {
                initialImageLoaded: !1,
                loadImageInSlide: function(e, t) {
                    if ("undefined" != typeof e && ("undefined" == typeof t && (t = !0), 0 !== T.slides.length)) {
                        var r = T.slides.eq(e), s = r.find(".swiper-lazy:not(.swiper-lazy-loaded):not(.swiper-lazy-loading)");
                        !r.hasClass("swiper-lazy") || r.hasClass("swiper-lazy-loaded") || r.hasClass("swiper-lazy-loading") || (s = s.add(r[0])), 
                        0 !== s.length && s.each(function() {
                            var e = a(this);
                            e.addClass("swiper-lazy-loading");
                            var s = e.attr("data-background"), i = e.attr("data-src"), n = e.attr("data-srcset");
                            T.loadImage(e[0], i || s, n, !1, function() {
                                if (s ? (e.css("background-image", "url(" + s + ")"), e.removeAttr("data-background")) : (n && (e.attr("srcset", n), 
                                e.removeAttr("data-srcset")), i && (e.attr("src", i), e.removeAttr("data-src"))), 
                                e.addClass("swiper-lazy-loaded").removeClass("swiper-lazy-loading"), r.find(".swiper-lazy-preloader, .preloader").remove(), 
                                T.params.loop && t) {
                                    var a = r.attr("data-swiper-slide-index");
                                    if (r.hasClass(T.params.slideDuplicateClass)) {
                                        var o = T.wrapper.children('[data-swiper-slide-index="' + a + '"]:not(.' + T.params.slideDuplicateClass + ")");
                                        T.lazy.loadImageInSlide(o.index(), !1);
                                    } else {
                                        var l = T.wrapper.children("." + T.params.slideDuplicateClass + '[data-swiper-slide-index="' + a + '"]');
                                        T.lazy.loadImageInSlide(l.index(), !1);
                                    }
                                }
                                T.emit("onLazyImageReady", T, r[0], e[0]);
                            }), T.emit("onLazyImageLoad", T, r[0], e[0]);
                        });
                    }
                },
                load: function() {
                    var e;
                    if (T.params.watchSlidesVisibility) T.wrapper.children("." + T.params.slideVisibleClass).each(function() {
                        T.lazy.loadImageInSlide(a(this).index());
                    }); else if (T.params.slidesPerView > 1) for (e = T.activeIndex; e < T.activeIndex + T.params.slidesPerView; e++) T.slides[e] && T.lazy.loadImageInSlide(e); else T.lazy.loadImageInSlide(T.activeIndex);
                    if (T.params.lazyLoadingInPrevNext) if (T.params.slidesPerView > 1) {
                        for (e = T.activeIndex + T.params.slidesPerView; e < T.activeIndex + T.params.slidesPerView + T.params.slidesPerView; e++) T.slides[e] && T.lazy.loadImageInSlide(e);
                        for (e = T.activeIndex - T.params.slidesPerView; e < T.activeIndex; e++) T.slides[e] && T.lazy.loadImageInSlide(e);
                    } else {
                        var t = T.wrapper.children("." + T.params.slideNextClass);
                        t.length > 0 && T.lazy.loadImageInSlide(t.index());
                        var r = T.wrapper.children("." + T.params.slidePrevClass);
                        r.length > 0 && T.lazy.loadImageInSlide(r.index());
                    }
                },
                onTransitionStart: function() {
                    T.params.lazyLoading && (T.params.lazyLoadingOnTransitionStart || !T.params.lazyLoadingOnTransitionStart && !T.lazy.initialImageLoaded) && T.lazy.load();
                },
                onTransitionEnd: function() {
                    T.params.lazyLoading && !T.params.lazyLoadingOnTransitionStart && T.lazy.load();
                }
            }, T.scrollbar = {
                isTouched: !1,
                setDragPosition: function(e) {
                    var a = T.scrollbar, t = i() ? "touchstart" === e.type || "touchmove" === e.type ? e.targetTouches[0].pageX : e.pageX || e.clientX : "touchstart" === e.type || "touchmove" === e.type ? e.targetTouches[0].pageY : e.pageY || e.clientY, r = t - a.track.offset()[i() ? "left" : "top"] - a.dragSize / 2, s = -T.minTranslate() * a.moveDivider, n = -T.maxTranslate() * a.moveDivider;
                    s > r ? r = s : r > n && (r = n), r = -r / a.moveDivider, T.updateProgress(r), T.setWrapperTranslate(r, !0);
                },
                dragStart: function(e) {
                    var a = T.scrollbar;
                    a.isTouched = !0, e.preventDefault(), e.stopPropagation(), a.setDragPosition(e), 
                    clearTimeout(a.dragTimeout), a.track.transition(0), T.params.scrollbarHide && a.track.css("opacity", 1), 
                    T.wrapper.transition(100), a.drag.transition(100), T.emit("onScrollbarDragStart", T);
                },
                dragMove: function(e) {
                    var a = T.scrollbar;
                    a.isTouched && (e.preventDefault ? e.preventDefault() : e.returnValue = !1, a.setDragPosition(e), 
                    T.wrapper.transition(0), a.track.transition(0), a.drag.transition(0), T.emit("onScrollbarDragMove", T));
                },
                dragEnd: function(e) {
                    var a = T.scrollbar;
                    a.isTouched && (a.isTouched = !1, T.params.scrollbarHide && (clearTimeout(a.dragTimeout), 
                    a.dragTimeout = setTimeout(function() {
                        a.track.css("opacity", 0), a.track.transition(400);
                    }, 1e3)), T.emit("onScrollbarDragEnd", T), T.params.scrollbarSnapOnRelease && T.slideReset());
                },
                enableDraggable: function() {
                    var e = T.scrollbar, t = T.support.touch ? e.track : document;
                    a(e.track).on(T.touchEvents.start, e.dragStart), a(t).on(T.touchEvents.move, e.dragMove), 
                    a(t).on(T.touchEvents.end, e.dragEnd);
                },
                disableDraggable: function() {
                    var e = T.scrollbar, t = T.support.touch ? e.track : document;
                    a(e.track).off(T.touchEvents.start, e.dragStart), a(t).off(T.touchEvents.move, e.dragMove), 
                    a(t).off(T.touchEvents.end, e.dragEnd);
                },
                set: function() {
                    if (T.params.scrollbar) {
                        var e = T.scrollbar;
                        e.track = a(T.params.scrollbar), e.drag = e.track.find(".swiper-scrollbar-drag"), 
                        0 === e.drag.length && (e.drag = a('<div class="swiper-scrollbar-drag"></div>'), 
                        e.track.append(e.drag)), e.drag[0].style.width = "", e.drag[0].style.height = "", 
                        e.trackSize = i() ? e.track[0].offsetWidth : e.track[0].offsetHeight, e.divider = T.size / T.virtualSize, 
                        e.moveDivider = e.divider * (e.trackSize / T.size), e.dragSize = e.trackSize * e.divider, 
                        i() ? e.drag[0].style.width = e.dragSize + "px" : e.drag[0].style.height = e.dragSize + "px", 
                        e.divider >= 1 ? e.track[0].style.display = "none" : e.track[0].style.display = "", 
                        T.params.scrollbarHide && (e.track[0].style.opacity = 0);
                    }
                },
                setTranslate: function() {
                    if (T.params.scrollbar) {
                        var e, a = T.scrollbar, t = (T.translate || 0, a.dragSize);
                        e = (a.trackSize - a.dragSize) * T.progress, T.rtl && i() ? (e = -e, e > 0 ? (t = a.dragSize - e, 
                        e = 0) : -e + a.dragSize > a.trackSize && (t = a.trackSize + e)) : 0 > e ? (t = a.dragSize + e, 
                        e = 0) : e + a.dragSize > a.trackSize && (t = a.trackSize - e), i() ? (T.support.transforms3d ? a.drag.transform("translate3d(" + e + "px, 0, 0)") : a.drag.transform("translateX(" + e + "px)"), 
                        a.drag[0].style.width = t + "px") : (T.support.transforms3d ? a.drag.transform("translate3d(0px, " + e + "px, 0)") : a.drag.transform("translateY(" + e + "px)"), 
                        a.drag[0].style.height = t + "px"), T.params.scrollbarHide && (clearTimeout(a.timeout), 
                        a.track[0].style.opacity = 1, a.timeout = setTimeout(function() {
                            a.track[0].style.opacity = 0, a.track.transition(400);
                        }, 1e3));
                    }
                },
                setTransition: function(e) {
                    T.params.scrollbar && T.scrollbar.drag.transition(e);
                }
            }, T.controller = {
                LinearSpline: function(e, a) {
                    this.x = e, this.y = a, this.lastIndex = e.length - 1;
                    var t, r;
                    this.x.length;
                    this.interpolate = function(e) {
                        return e ? (r = s(this.x, e), t = r - 1, (e - this.x[t]) * (this.y[r] - this.y[t]) / (this.x[r] - this.x[t]) + this.y[t]) : 0;
                    };
                    var s = function() {
                        var e, a, t;
                        return function(r, s) {
                            for (a = -1, e = r.length; e - a > 1; ) r[t = e + a >> 1] <= s ? a = t : e = t;
                            return e;
                        };
                    }();
                },
                getInterpolateFunction: function(e) {
                    T.controller.spline || (T.controller.spline = T.params.loop ? new T.controller.LinearSpline(T.slidesGrid, e.slidesGrid) : new T.controller.LinearSpline(T.snapGrid, e.snapGrid));
                },
                setTranslate: function(e, a) {
                    function r(a) {
                        e = a.rtl && "horizontal" === a.params.direction ? -T.translate : T.translate, "slide" === T.params.controlBy && (T.controller.getInterpolateFunction(a), 
                        i = -T.controller.spline.interpolate(-e)), i && "container" !== T.params.controlBy || (s = (a.maxTranslate() - a.minTranslate()) / (T.maxTranslate() - T.minTranslate()), 
                        i = (e - T.minTranslate()) * s + a.minTranslate()), T.params.controlInverse && (i = a.maxTranslate() - i), 
                        a.updateProgress(i), a.setWrapperTranslate(i, !1, T), a.updateActiveIndex();
                    }
                    var s, i, n = T.params.control;
                    if (T.isArray(n)) for (var o = 0; o < n.length; o++) n[o] !== a && n[o] instanceof t && r(n[o]); else n instanceof t && a !== n && r(n);
                },
                setTransition: function(e, a) {
                    function r(a) {
                        a.setWrapperTransition(e, T), 0 !== e && (a.onTransitionStart(), a.wrapper.transitionEnd(function() {
                            i && (a.params.loop && "slide" === T.params.controlBy && a.fixLoop(), a.onTransitionEnd());
                        }));
                    }
                    var s, i = T.params.control;
                    if (T.isArray(i)) for (s = 0; s < i.length; s++) i[s] !== a && i[s] instanceof t && r(i[s]); else i instanceof t && a !== i && r(i);
                }
            }, T.hashnav = {
                init: function() {
                    if (T.params.hashnav) {
                        T.hashnav.initialized = !0;
                        var e = document.location.hash.replace("#", "");
                        if (e) for (var a = 0, t = 0, r = T.slides.length; r > t; t++) {
                            var s = T.slides.eq(t), i = s.attr("data-hash");
                            if (i === e && !s.hasClass(T.params.slideDuplicateClass)) {
                                var n = s.index();
                                T.slideTo(n, a, T.params.runCallbacksOnInit, !0);
                            }
                        }
                    }
                },
                setHash: function() {
                    T.hashnav.initialized && T.params.hashnav && (document.location.hash = T.slides.eq(T.activeIndex).attr("data-hash") || "");
                }
            }, T.disableKeyboardControl = function() {
                T.params.keyboardControl = !1, a(document).off("keydown", p);
            }, T.enableKeyboardControl = function() {
                T.params.keyboardControl = !0, a(document).on("keydown", p);
            }, T.mousewheel = {
                event: !1,
                lastScrollTime: new window.Date().getTime()
            }, T.params.mousewheelControl) {
                try {
                    new window.WheelEvent("wheel"), T.mousewheel.event = "wheel";
                } catch (R) {}
                T.mousewheel.event || void 0 === document.onmousewheel || (T.mousewheel.event = "mousewheel"), 
                T.mousewheel.event || (T.mousewheel.event = "DOMMouseScroll");
            }
            T.disableMousewheelControl = function() {
                return T.mousewheel.event ? (T.container.off(T.mousewheel.event, u), !0) : !1;
            }, T.enableMousewheelControl = function() {
                return T.mousewheel.event ? (T.container.on(T.mousewheel.event, u), !0) : !1;
            }, T.parallax = {
                setTranslate: function() {
                    T.container.children("[data-swiper-parallax], [data-swiper-parallax-x], [data-swiper-parallax-y]").each(function() {
                        c(this, T.progress);
                    }), T.slides.each(function() {
                        var e = a(this);
                        e.find("[data-swiper-parallax], [data-swiper-parallax-x], [data-swiper-parallax-y]").each(function() {
                            var a = Math.min(Math.max(e[0].progress, -1), 1);
                            c(this, a);
                        });
                    });
                },
                setTransition: function(e) {
                    "undefined" == typeof e && (e = T.params.speed), T.container.find("[data-swiper-parallax], [data-swiper-parallax-x], [data-swiper-parallax-y]").each(function() {
                        var t = a(this), r = parseInt(t.attr("data-swiper-parallax-duration"), 10) || e;
                        0 === e && (r = 0), t.transition(r);
                    });
                }
            }, T._plugins = [];
            for (var W in T.plugins) {
                var V = T.plugins[W](T, T.params[W]);
                V && T._plugins.push(V);
            }
            return T.callPlugins = function(e) {
                for (var a = 0; a < T._plugins.length; a++) e in T._plugins[a] && T._plugins[a][e](arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
            }, T.emitterEventListeners = {}, T.emit = function(e) {
                T.params[e] && T.params[e](arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
                var a;
                if (T.emitterEventListeners[e]) for (a = 0; a < T.emitterEventListeners[e].length; a++) T.emitterEventListeners[e][a](arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
                T.callPlugins && T.callPlugins(e, arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
            }, T.on = function(e, a) {
                return e = m(e), T.emitterEventListeners[e] || (T.emitterEventListeners[e] = []), 
                T.emitterEventListeners[e].push(a), T;
            }, T.off = function(e, a) {
                var t;
                if (e = m(e), "undefined" == typeof a) return T.emitterEventListeners[e] = [], T;
                if (T.emitterEventListeners[e] && 0 !== T.emitterEventListeners[e].length) {
                    for (t = 0; t < T.emitterEventListeners[e].length; t++) T.emitterEventListeners[e][t] === a && T.emitterEventListeners[e].splice(t, 1);
                    return T;
                }
            }, T.once = function(e, a) {
                e = m(e);
                var t = function() {
                    a(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]), T.off(e, t);
                };
                return T.on(e, t), T;
            }, T.a11y = {
                makeFocusable: function(e) {
                    return e.attr("tabIndex", "0"), e;
                },
                addRole: function(e, a) {
                    return e.attr("role", a), e;
                },
                addLabel: function(e, a) {
                    return e.attr("aria-label", a), e;
                },
                disable: function(e) {
                    return e.attr("aria-disabled", !0), e;
                },
                enable: function(e) {
                    return e.attr("aria-disabled", !1), e;
                },
                onEnterKey: function(e) {
                    13 === e.keyCode && (a(e.target).is(T.params.nextButton) ? (T.onClickNext(e), T.isEnd ? T.a11y.notify(T.params.lastSlideMessage) : T.a11y.notify(T.params.nextSlideMessage)) : a(e.target).is(T.params.prevButton) && (T.onClickPrev(e), 
                    T.isBeginning ? T.a11y.notify(T.params.firstSlideMessage) : T.a11y.notify(T.params.prevSlideMessage)), 
                    a(e.target).is("." + T.params.bulletClass) && a(e.target)[0].click());
                },
                liveRegion: a('<span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span>'),
                notify: function(e) {
                    var a = T.a11y.liveRegion;
                    0 !== a.length && (a.html(""), a.html(e));
                },
                init: function() {
                    if (T.params.nextButton) {
                        var e = a(T.params.nextButton);
                        T.a11y.makeFocusable(e), T.a11y.addRole(e, "button"), T.a11y.addLabel(e, T.params.nextSlideMessage);
                    }
                    if (T.params.prevButton) {
                        var t = a(T.params.prevButton);
                        T.a11y.makeFocusable(t), T.a11y.addRole(t, "button"), T.a11y.addLabel(t, T.params.prevSlideMessage);
                    }
                    a(T.container).append(T.a11y.liveRegion);
                },
                initPagination: function() {
                    T.params.pagination && T.params.paginationClickable && T.bullets && T.bullets.length && T.bullets.each(function() {
                        var e = a(this);
                        T.a11y.makeFocusable(e), T.a11y.addRole(e, "button"), T.a11y.addLabel(e, T.params.paginationBulletMessage.replace(/{{index}}/, e.index() + 1));
                    });
                },
                destroy: function() {
                    T.a11y.liveRegion && T.a11y.liveRegion.length > 0 && T.a11y.liveRegion.remove();
                }
            }, T.init = function() {
                T.params.loop && T.createLoop(), T.updateContainerSize(), T.updateSlidesSize(), 
                T.updatePagination(), T.params.scrollbar && T.scrollbar && (T.scrollbar.set(), T.params.scrollbarDraggable && T.scrollbar.enableDraggable()), 
                "slide" !== T.params.effect && T.effects[T.params.effect] && (T.params.loop || T.updateProgress(), 
                T.effects[T.params.effect].setTranslate()), T.params.loop ? T.slideTo(T.params.initialSlide + T.loopedSlides, 0, T.params.runCallbacksOnInit) : (T.slideTo(T.params.initialSlide, 0, T.params.runCallbacksOnInit), 
                0 === T.params.initialSlide && (T.parallax && T.params.parallax && T.parallax.setTranslate(), 
                T.lazy && T.params.lazyLoading && (T.lazy.load(), T.lazy.initialImageLoaded = !0))), 
                T.attachEvents(), T.params.observer && T.support.observer && T.initObservers(), 
                T.params.preloadImages && !T.params.lazyLoading && T.preloadImages(), T.params.autoplay && T.startAutoplay(), 
                T.params.keyboardControl && T.enableKeyboardControl && T.enableKeyboardControl(), 
                T.params.mousewheelControl && T.enableMousewheelControl && T.enableMousewheelControl(), 
                T.params.hashnav && T.hashnav && T.hashnav.init(), T.params.a11y && T.a11y && T.a11y.init(), 
                T.emit("onInit", T);
            }, T.cleanupStyles = function() {
                T.container.removeClass(T.classNames.join(" ")).removeAttr("style"), T.wrapper.removeAttr("style"), 
                T.slides && T.slides.length && T.slides.removeClass([ T.params.slideVisibleClass, T.params.slideActiveClass, T.params.slideNextClass, T.params.slidePrevClass ].join(" ")).removeAttr("style").removeAttr("data-swiper-column").removeAttr("data-swiper-row"), 
                T.paginationContainer && T.paginationContainer.length && T.paginationContainer.removeClass(T.params.paginationHiddenClass), 
                T.bullets && T.bullets.length && T.bullets.removeClass(T.params.bulletActiveClass), 
                T.params.prevButton && a(T.params.prevButton).removeClass(T.params.buttonDisabledClass), 
                T.params.nextButton && a(T.params.nextButton).removeClass(T.params.buttonDisabledClass), 
                T.params.scrollbar && T.scrollbar && (T.scrollbar.track && T.scrollbar.track.length && T.scrollbar.track.removeAttr("style"), 
                T.scrollbar.drag && T.scrollbar.drag.length && T.scrollbar.drag.removeAttr("style"));
            }, T.destroy = function(e, a) {
                T.detachEvents(), T.stopAutoplay(), T.params.scrollbar && T.scrollbar && T.params.scrollbarDraggable && T.scrollbar.disableDraggable(), 
                T.params.loop && T.destroyLoop(), a && T.cleanupStyles(), T.disconnectObservers(), 
                T.params.keyboardControl && T.disableKeyboardControl && T.disableKeyboardControl(), 
                T.params.mousewheelControl && T.disableMousewheelControl && T.disableMousewheelControl(), 
                T.params.a11y && T.a11y && T.a11y.destroy(), T.emit("onDestroy"), e !== !1 && (T = null);
            }, T.init(), T;
        }
    };
    t.prototype = {
        isSafari: function() {
            var e = navigator.userAgent.toLowerCase();
            return e.indexOf("safari") >= 0 && e.indexOf("chrome") < 0 && e.indexOf("android") < 0;
        }(),
        isUiWebView: /(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/i.test(navigator.userAgent),
        isArray: function(e) {
            return "[object Array]" === Object.prototype.toString.apply(e);
        },
        browser: {
            ie: window.navigator.pointerEnabled || window.navigator.msPointerEnabled,
            ieTouch: window.navigator.msPointerEnabled && window.navigator.msMaxTouchPoints > 1 || window.navigator.pointerEnabled && window.navigator.maxTouchPoints > 1
        },
        device: function() {
            var e = navigator.userAgent, a = e.match(/(Android);?[\s\/]+([\d.]+)?/), t = e.match(/(iPad).*OS\s([\d_]+)/), r = e.match(/(iPod)(.*OS\s([\d_]+))?/), s = !t && e.match(/(iPhone\sOS)\s([\d_]+)/);
            return {
                ios: t || s || r,
                android: a
            };
        }(),
        support: {
            touch: window.Modernizr && Modernizr.touch === !0 || function() {
                return !!("ontouchstart" in window || window.DocumentTouch && document instanceof DocumentTouch);
            }(),
            transforms3d: window.Modernizr && Modernizr.csstransforms3d === !0 || function() {
                var e = document.createElement("div").style;
                return "webkitPerspective" in e || "MozPerspective" in e || "OPerspective" in e || "MsPerspective" in e || "perspective" in e;
            }(),
            flexbox: function() {
                for (var e = document.createElement("div").style, a = "alignItems webkitAlignItems webkitBoxAlign msFlexAlign mozBoxAlign webkitFlexDirection msFlexDirection mozBoxDirection mozBoxOrient webkitBoxDirection webkitBoxOrient".split(" "), t = 0; t < a.length; t++) if (a[t] in e) return !0;
            }(),
            observer: function() {
                return "MutationObserver" in window || "WebkitMutationObserver" in window;
            }()
        },
        plugins: {}
    };
    for (var r = (function() {
        var e = function(e) {
            var a = this, t = 0;
            for (t = 0; t < e.length; t++) a[t] = e[t];
            return a.length = e.length, this;
        }, a = function(a, t) {
            var r = [], s = 0;
            if (a && !t && a instanceof e) return a;
            if (a) if ("string" == typeof a) {
                var i, n, o = a.trim();
                if (o.indexOf("<") >= 0 && o.indexOf(">") >= 0) {
                    var l = "div";
                    for (0 === o.indexOf("<li") && (l = "ul"), 0 === o.indexOf("<tr") && (l = "tbody"), 
                    (0 === o.indexOf("<td") || 0 === o.indexOf("<th")) && (l = "tr"), 0 === o.indexOf("<tbody") && (l = "table"), 
                    0 === o.indexOf("<option") && (l = "select"), n = document.createElement(l), n.innerHTML = a, 
                    s = 0; s < n.childNodes.length; s++) r.push(n.childNodes[s]);
                } else for (i = t || "#" !== a[0] || a.match(/[ .<>:~]/) ? (t || document).querySelectorAll(a) : [ document.getElementById(a.split("#")[1]) ], 
                s = 0; s < i.length; s++) i[s] && r.push(i[s]);
            } else if (a.nodeType || a === window || a === document) r.push(a); else if (a.length > 0 && a[0].nodeType) for (s = 0; s < a.length; s++) r.push(a[s]);
            return new e(r);
        };
        return e.prototype = {
            addClass: function(e) {
                if ("undefined" == typeof e) return this;
                for (var a = e.split(" "), t = 0; t < a.length; t++) for (var r = 0; r < this.length; r++) this[r].classList.add(a[t]);
                return this;
            },
            removeClass: function(e) {
                for (var a = e.split(" "), t = 0; t < a.length; t++) for (var r = 0; r < this.length; r++) this[r].classList.remove(a[t]);
                return this;
            },
            hasClass: function(e) {
                return this[0] ? this[0].classList.contains(e) : !1;
            },
            toggleClass: function(e) {
                for (var a = e.split(" "), t = 0; t < a.length; t++) for (var r = 0; r < this.length; r++) this[r].classList.toggle(a[t]);
                return this;
            },
            attr: function(e, a) {
                if (1 === arguments.length && "string" == typeof e) return this[0] ? this[0].getAttribute(e) : void 0;
                for (var t = 0; t < this.length; t++) if (2 === arguments.length) this[t].setAttribute(e, a); else for (var r in e) this[t][r] = e[r], 
                this[t].setAttribute(r, e[r]);
                return this;
            },
            removeAttr: function(e) {
                for (var a = 0; a < this.length; a++) this[a].removeAttribute(e);
                return this;
            },
            data: function(e, a) {
                if ("undefined" != typeof a) {
                    for (var t = 0; t < this.length; t++) {
                        var r = this[t];
                        r.dom7ElementDataStorage || (r.dom7ElementDataStorage = {}), r.dom7ElementDataStorage[e] = a;
                    }
                    return this;
                }
                if (this[0]) {
                    var s = this[0].getAttribute("data-" + e);
                    return s ? s : this[0].dom7ElementDataStorage && e in this[0].dom7ElementDataStorage ? this[0].dom7ElementDataStorage[e] : void 0;
                }
            },
            transform: function(e) {
                for (var a = 0; a < this.length; a++) {
                    var t = this[a].style;
                    t.webkitTransform = t.MsTransform = t.msTransform = t.MozTransform = t.OTransform = t.transform = e;
                }
                return this;
            },
            transition: function(e) {
                "string" != typeof e && (e += "ms");
                for (var a = 0; a < this.length; a++) {
                    var t = this[a].style;
                    t.webkitTransitionDuration = t.MsTransitionDuration = t.msTransitionDuration = t.MozTransitionDuration = t.OTransitionDuration = t.transitionDuration = e;
                }
                return this;
            },
            on: function(e, t, r, s) {
                function i(e) {
                    var s = e.target;
                    if (a(s).is(t)) r.call(s, e); else for (var i = a(s).parents(), n = 0; n < i.length; n++) a(i[n]).is(t) && r.call(i[n], e);
                }
                var n, o, l = e.split(" ");
                for (n = 0; n < this.length; n++) if ("function" == typeof t || t === !1) for ("function" == typeof t && (r = arguments[1], 
                s = arguments[2] || !1), o = 0; o < l.length; o++) this[n].addEventListener(l[o], r, s); else for (o = 0; o < l.length; o++) this[n].dom7LiveListeners || (this[n].dom7LiveListeners = []), 
                this[n].dom7LiveListeners.push({
                    listener: r,
                    liveListener: i
                }), this[n].addEventListener(l[o], i, s);
                return this;
            },
            off: function(e, a, t, r) {
                for (var s = e.split(" "), i = 0; i < s.length; i++) for (var n = 0; n < this.length; n++) if ("function" == typeof a || a === !1) "function" == typeof a && (t = arguments[1], 
                r = arguments[2] || !1), this[n].removeEventListener(s[i], t, r); else if (this[n].dom7LiveListeners) for (var o = 0; o < this[n].dom7LiveListeners.length; o++) this[n].dom7LiveListeners[o].listener === t && this[n].removeEventListener(s[i], this[n].dom7LiveListeners[o].liveListener, r);
                return this;
            },
            once: function(e, a, t, r) {
                function s(n) {
                    t(n), i.off(e, a, s, r);
                }
                var i = this;
                "function" == typeof a && (a = !1, t = arguments[1], r = arguments[2]), i.on(e, a, s, r);
            },
            trigger: function(e, a) {
                for (var t = 0; t < this.length; t++) {
                    var r;
                    try {
                        r = new window.CustomEvent(e, {
                            detail: a,
                            bubbles: !0,
                            cancelable: !0
                        });
                    } catch (s) {
                        r = document.createEvent("Event"), r.initEvent(e, !0, !0), r.detail = a;
                    }
                    this[t].dispatchEvent(r);
                }
                return this;
            },
            transitionEnd: function(e) {
                function a(i) {
                    if (i.target === this) for (e.call(this, i), t = 0; t < r.length; t++) s.off(r[t], a);
                }
                var t, r = [ "webkitTransitionEnd", "transitionend", "oTransitionEnd", "MSTransitionEnd", "msTransitionEnd" ], s = this;
                if (e) for (t = 0; t < r.length; t++) s.on(r[t], a);
                return this;
            },
            width: function() {
                return this[0] === window ? window.innerWidth : this.length > 0 ? parseFloat(this.css("width")) : null;
            },
            outerWidth: function(e) {
                return this.length > 0 ? e ? this[0].offsetWidth + parseFloat(this.css("margin-right")) + parseFloat(this.css("margin-left")) : this[0].offsetWidth : null;
            },
            height: function() {
                return this[0] === window ? window.innerHeight : this.length > 0 ? parseFloat(this.css("height")) : null;
            },
            outerHeight: function(e) {
                return this.length > 0 ? e ? this[0].offsetHeight + parseFloat(this.css("margin-top")) + parseFloat(this.css("margin-bottom")) : this[0].offsetHeight : null;
            },
            offset: function() {
                if (this.length > 0) {
                    var e = this[0], a = e.getBoundingClientRect(), t = document.body, r = e.clientTop || t.clientTop || 0, s = e.clientLeft || t.clientLeft || 0, i = window.pageYOffset || e.scrollTop, n = window.pageXOffset || e.scrollLeft;
                    return {
                        top: a.top + i - r,
                        left: a.left + n - s
                    };
                }
                return null;
            },
            css: function(e, a) {
                var t;
                if (1 === arguments.length) {
                    if ("string" != typeof e) {
                        for (t = 0; t < this.length; t++) for (var r in e) this[t].style[r] = e[r];
                        return this;
                    }
                    if (this[0]) return window.getComputedStyle(this[0], null).getPropertyValue(e);
                }
                if (2 === arguments.length && "string" == typeof e) {
                    for (t = 0; t < this.length; t++) this[t].style[e] = a;
                    return this;
                }
                return this;
            },
            each: function(e) {
                for (var a = 0; a < this.length; a++) e.call(this[a], a, this[a]);
                return this;
            },
            html: function(e) {
                if ("undefined" == typeof e) return this[0] ? this[0].innerHTML : void 0;
                for (var a = 0; a < this.length; a++) this[a].innerHTML = e;
                return this;
            },
            is: function(t) {
                if (!this[0]) return !1;
                var r, s;
                if ("string" == typeof t) {
                    var i = this[0];
                    if (i === document) return t === document;
                    if (i === window) return t === window;
                    if (i.matches) return i.matches(t);
                    if (i.webkitMatchesSelector) return i.webkitMatchesSelector(t);
                    if (i.mozMatchesSelector) return i.mozMatchesSelector(t);
                    if (i.msMatchesSelector) return i.msMatchesSelector(t);
                    for (r = a(t), s = 0; s < r.length; s++) if (r[s] === this[0]) return !0;
                    return !1;
                }
                if (t === document) return this[0] === document;
                if (t === window) return this[0] === window;
                if (t.nodeType || t instanceof e) {
                    for (r = t.nodeType ? [ t ] : t, s = 0; s < r.length; s++) if (r[s] === this[0]) return !0;
                    return !1;
                }
                return !1;
            },
            index: function() {
                if (this[0]) {
                    for (var e = this[0], a = 0; null !== (e = e.previousSibling); ) 1 === e.nodeType && a++;
                    return a;
                }
            },
            eq: function(a) {
                if ("undefined" == typeof a) return this;
                var t, r = this.length;
                return a > r - 1 ? new e([]) : 0 > a ? (t = r + a, new e(0 > t ? [] : [ this[t] ])) : new e([ this[a] ]);
            },
            append: function(a) {
                var t, r;
                for (t = 0; t < this.length; t++) if ("string" == typeof a) {
                    var s = document.createElement("div");
                    for (s.innerHTML = a; s.firstChild; ) this[t].appendChild(s.firstChild);
                } else if (a instanceof e) for (r = 0; r < a.length; r++) this[t].appendChild(a[r]); else this[t].appendChild(a);
                return this;
            },
            prepend: function(a) {
                var t, r;
                for (t = 0; t < this.length; t++) if ("string" == typeof a) {
                    var s = document.createElement("div");
                    for (s.innerHTML = a, r = s.childNodes.length - 1; r >= 0; r--) this[t].insertBefore(s.childNodes[r], this[t].childNodes[0]);
                } else if (a instanceof e) for (r = 0; r < a.length; r++) this[t].insertBefore(a[r], this[t].childNodes[0]); else this[t].insertBefore(a, this[t].childNodes[0]);
                return this;
            },
            insertBefore: function(e) {
                for (var t = a(e), r = 0; r < this.length; r++) if (1 === t.length) t[0].parentNode.insertBefore(this[r], t[0]); else if (t.length > 1) for (var s = 0; s < t.length; s++) t[s].parentNode.insertBefore(this[r].cloneNode(!0), t[s]);
            },
            insertAfter: function(e) {
                for (var t = a(e), r = 0; r < this.length; r++) if (1 === t.length) t[0].parentNode.insertBefore(this[r], t[0].nextSibling); else if (t.length > 1) for (var s = 0; s < t.length; s++) t[s].parentNode.insertBefore(this[r].cloneNode(!0), t[s].nextSibling);
            },
            next: function(t) {
                return new e(this.length > 0 ? t ? this[0].nextElementSibling && a(this[0].nextElementSibling).is(t) ? [ this[0].nextElementSibling ] : [] : this[0].nextElementSibling ? [ this[0].nextElementSibling ] : [] : []);
            },
            nextAll: function(t) {
                var r = [], s = this[0];
                if (!s) return new e([]);
                for (;s.nextElementSibling; ) {
                    var i = s.nextElementSibling;
                    t ? a(i).is(t) && r.push(i) : r.push(i), s = i;
                }
                return new e(r);
            },
            prev: function(t) {
                return new e(this.length > 0 ? t ? this[0].previousElementSibling && a(this[0].previousElementSibling).is(t) ? [ this[0].previousElementSibling ] : [] : this[0].previousElementSibling ? [ this[0].previousElementSibling ] : [] : []);
            },
            prevAll: function(t) {
                var r = [], s = this[0];
                if (!s) return new e([]);
                for (;s.previousElementSibling; ) {
                    var i = s.previousElementSibling;
                    t ? a(i).is(t) && r.push(i) : r.push(i), s = i;
                }
                return new e(r);
            },
            parent: function(e) {
                for (var t = [], r = 0; r < this.length; r++) e ? a(this[r].parentNode).is(e) && t.push(this[r].parentNode) : t.push(this[r].parentNode);
                return a(a.unique(t));
            },
            parents: function(e) {
                for (var t = [], r = 0; r < this.length; r++) for (var s = this[r].parentNode; s; ) e ? a(s).is(e) && t.push(s) : t.push(s), 
                s = s.parentNode;
                return a(a.unique(t));
            },
            find: function(a) {
                for (var t = [], r = 0; r < this.length; r++) for (var s = this[r].querySelectorAll(a), i = 0; i < s.length; i++) t.push(s[i]);
                return new e(t);
            },
            children: function(t) {
                for (var r = [], s = 0; s < this.length; s++) for (var i = this[s].childNodes, n = 0; n < i.length; n++) t ? 1 === i[n].nodeType && a(i[n]).is(t) && r.push(i[n]) : 1 === i[n].nodeType && r.push(i[n]);
                return new e(a.unique(r));
            },
            remove: function() {
                for (var e = 0; e < this.length; e++) this[e].parentNode && this[e].parentNode.removeChild(this[e]);
                return this;
            },
            add: function() {
                var e, t, r = this;
                for (e = 0; e < arguments.length; e++) {
                    var s = a(arguments[e]);
                    for (t = 0; t < s.length; t++) r[r.length] = s[t], r.length++;
                }
                return r;
            }
        }, a.fn = e.prototype, a.unique = function(e) {
            for (var a = [], t = 0; t < e.length; t++) -1 === a.indexOf(e[t]) && a.push(e[t]);
            return a;
        }, a;
    }()), s = [ "jQuery", "Zepto", "Dom7" ], i = 0; i < s.length; i++) window[s[i]] && e(window[s[i]]);
    var n;
    n = "undefined" == typeof r ? window.Dom7 || window.Zepto || window.jQuery : r, 
    n && ("transitionEnd" in n.fn || (n.fn.transitionEnd = function(e) {
        function a(i) {
            if (i.target === this) for (e.call(this, i), t = 0; t < r.length; t++) s.off(r[t], a);
        }
        var t, r = [ "webkitTransitionEnd", "transitionend", "oTransitionEnd", "MSTransitionEnd", "msTransitionEnd" ], s = this;
        if (e) for (t = 0; t < r.length; t++) s.on(r[t], a);
        return this;
    }), "transform" in n.fn || (n.fn.transform = function(e) {
        for (var a = 0; a < this.length; a++) {
            var t = this[a].style;
            t.webkitTransform = t.MsTransform = t.msTransform = t.MozTransform = t.OTransform = t.transform = e;
        }
        return this;
    }), "transition" in n.fn || (n.fn.transition = function(e) {
        "string" != typeof e && (e += "ms");
        for (var a = 0; a < this.length; a++) {
            var t = this[a].style;
            t.webkitTransitionDuration = t.MsTransitionDuration = t.msTransitionDuration = t.MozTransitionDuration = t.OTransitionDuration = t.transitionDuration = e;
        }
        return this;
    })), window.Swiper = t;
}(), "undefined" != typeof module ? module.exports = window.Swiper : "function" == typeof define && define.amd && define([], function() {
    "use strict";
    return window.Swiper;
});

/*! Magnific Popup - v1.0.1 - 2015-12-30
* http://dimsemenov.com/plugins/magnific-popup/
* Copyright (c) 2015 Dmitry Semenov; */
!function(a) {
    "function" == typeof define && define.amd ? define([ "jquery" ], a) : a("object" == typeof exports ? require("jquery") : window.jQuery || window.Zepto);
}(function(a) {
    var b, c, d, e, f, g, h = "Close", i = "BeforeClose", j = "AfterClose", k = "BeforeAppend", l = "MarkupParse", m = "Open", n = "Change", o = "mfp", p = "." + o, q = "mfp-ready", r = "mfp-removing", s = "mfp-prevent-close", t = function() {}, u = !!window.jQuery, v = a(window), w = function(a, c) {
        b.ev.on(o + a + p, c);
    }, x = function(b, c, d, e) {
        var f = document.createElement("div");
        return f.className = "mfp-" + b, d && (f.innerHTML = d), e ? c && c.appendChild(f) : (f = a(f), 
        c && f.appendTo(c)), f;
    }, y = function(c, d) {
        b.ev.triggerHandler(o + c, d), b.st.callbacks && (c = c.charAt(0).toLowerCase() + c.slice(1), 
        b.st.callbacks[c] && b.st.callbacks[c].apply(b, a.isArray(d) ? d : [ d ]));
    }, z = function(c) {
        return c === g && b.currTemplate.closeBtn || (b.currTemplate.closeBtn = a(b.st.closeMarkup.replace("%title%", b.st.tClose)), 
        g = c), b.currTemplate.closeBtn;
    }, A = function() {
        a.magnificPopup.instance || (b = new t(), b.init(), a.magnificPopup.instance = b);
    }, B = function() {
        var a = document.createElement("p").style, b = [ "ms", "O", "Moz", "Webkit" ];
        if (void 0 !== a.transition) return !0;
        for (;b.length; ) if (b.pop() + "Transition" in a) return !0;
        return !1;
    };
    t.prototype = {
        constructor: t,
        init: function() {
            var c = navigator.appVersion;
            b.isIE7 = -1 !== c.indexOf("MSIE 7."), b.isIE8 = -1 !== c.indexOf("MSIE 8."), b.isLowIE = b.isIE7 || b.isIE8, 
            b.isAndroid = /android/gi.test(c), b.isIOS = /iphone|ipad|ipod/gi.test(c), b.supportsTransition = B(), 
            b.probablyMobile = b.isAndroid || b.isIOS || /(Opera Mini)|Kindle|webOS|BlackBerry|(Opera Mobi)|(Windows Phone)|IEMobile/i.test(navigator.userAgent), 
            d = a(document), b.popupsCache = {};
        },
        open: function(c) {
            var e;
            if (c.isObj === !1) {
                b.items = c.items.toArray(), b.index = 0;
                var g, h = c.items;
                for (e = 0; e < h.length; e++) if (g = h[e], g.parsed && (g = g.el[0]), g === c.el[0]) {
                    b.index = e;
                    break;
                }
            } else b.items = a.isArray(c.items) ? c.items : [ c.items ], b.index = c.index || 0;
            if (b.isOpen) return void b.updateItemHTML();
            b.types = [], f = "", c.mainEl && c.mainEl.length ? b.ev = c.mainEl.eq(0) : b.ev = d, 
            c.key ? (b.popupsCache[c.key] || (b.popupsCache[c.key] = {}), b.currTemplate = b.popupsCache[c.key]) : b.currTemplate = {}, 
            b.st = a.extend(!0, {}, a.magnificPopup.defaults, c), b.fixedContentPos = "auto" === b.st.fixedContentPos ? !b.probablyMobile : b.st.fixedContentPos, 
            b.st.modal && (b.st.closeOnContentClick = !1, b.st.closeOnBgClick = !1, b.st.showCloseBtn = !1, 
            b.st.enableEscapeKey = !1), b.bgOverlay || (b.bgOverlay = x("bg").on("click" + p, function() {
                b.close();
            }), b.wrap = x("wrap").attr("tabindex", -1).on("click" + p, function(a) {
                b._checkIfClose(a.target) && b.close();
            }), b.container = x("container", b.wrap)), b.contentContainer = x("content"), b.st.preloader && (b.preloader = x("preloader", b.container, b.st.tLoading));
            var i = a.magnificPopup.modules;
            for (e = 0; e < i.length; e++) {
                var j = i[e];
                j = j.charAt(0).toUpperCase() + j.slice(1), b["init" + j].call(b);
            }
            y("BeforeOpen"), b.st.showCloseBtn && (b.st.closeBtnInside ? (w(l, function(a, b, c, d) {
                c.close_replaceWith = z(d.type);
            }), f += " mfp-close-btn-in") : b.wrap.append(z())), b.st.alignTop && (f += " mfp-align-top"), 
            b.fixedContentPos ? b.wrap.css({
                overflow: b.st.overflowY,
                overflowX: "hidden",
                overflowY: b.st.overflowY
            }) : b.wrap.css({
                top: v.scrollTop(),
                position: "absolute"
            }), (b.st.fixedBgPos === !1 || "auto" === b.st.fixedBgPos && !b.fixedContentPos) && b.bgOverlay.css({
                height: d.height(),
                position: "absolute"
            }), b.st.enableEscapeKey && d.on("keyup" + p, function(a) {
                27 === a.keyCode && b.close();
            }), v.on("resize" + p, function() {
                b.updateSize();
            }), b.st.closeOnContentClick || (f += " mfp-auto-cursor"), f && b.wrap.addClass(f);
            var k = b.wH = v.height(), n = {};
            if (b.fixedContentPos && b._hasScrollBar(k)) {
                var o = b._getScrollbarSize();
                o && (n.marginRight = o);
            }
            b.fixedContentPos && (b.isIE7 ? a("body, html").css("overflow", "hidden") : n.overflow = "hidden");
            var r = b.st.mainClass;
            return b.isIE7 && (r += " mfp-ie7"), r && b._addClassToMFP(r), b.updateItemHTML(), 
            y("BuildControls"), a("html").css(n), b.bgOverlay.add(b.wrap).prependTo(b.st.prependTo || a(document.body)), 
            b._lastFocusedEl = document.activeElement, setTimeout(function() {
                b.content ? (b._addClassToMFP(q), b._setFocus()) : b.bgOverlay.addClass(q), d.on("focusin" + p, b._onFocusIn);
            }, 16), b.isOpen = !0, b.updateSize(k), y(m), c;
        },
        close: function() {
            b.isOpen && (y(i), b.isOpen = !1, b.st.removalDelay && !b.isLowIE && b.supportsTransition ? (b._addClassToMFP(r), 
            setTimeout(function() {
                b._close();
            }, b.st.removalDelay)) : b._close());
        },
        _close: function() {
            y(h);
            var c = r + " " + q + " ";
            if (b.bgOverlay.detach(), b.wrap.detach(), b.container.empty(), b.st.mainClass && (c += b.st.mainClass + " "), 
            b._removeClassFromMFP(c), b.fixedContentPos) {
                var e = {
                    marginRight: ""
                };
                b.isIE7 ? a("body, html").css("overflow", "") : e.overflow = "", a("html").css(e);
            }
            d.off("keyup" + p + " focusin" + p), b.ev.off(p), b.wrap.attr("class", "mfp-wrap").removeAttr("style"), 
            b.bgOverlay.attr("class", "mfp-bg"), b.container.attr("class", "mfp-container"), 
            !b.st.showCloseBtn || b.st.closeBtnInside && b.currTemplate[b.currItem.type] !== !0 || b.currTemplate.closeBtn && b.currTemplate.closeBtn.detach(), 
            b.st.autoFocusLast && b._lastFocusedEl && a(b._lastFocusedEl).focus(), b.currItem = null, 
            b.content = null, b.currTemplate = null, b.prevHeight = 0, y(j);
        },
        updateSize: function(a) {
            if (b.isIOS) {
                var c = document.documentElement.clientWidth / window.innerWidth, d = window.innerHeight * c;
                b.wrap.css("height", d), b.wH = d;
            } else b.wH = a || v.height();
            b.fixedContentPos || b.wrap.css("height", b.wH), y("Resize");
        },
        updateItemHTML: function() {
            var c = b.items[b.index];
            b.contentContainer.detach(), b.content && b.content.detach(), c.parsed || (c = b.parseEl(b.index));
            var d = c.type;
            if (y("BeforeChange", [ b.currItem ? b.currItem.type : "", d ]), b.currItem = c, 
            !b.currTemplate[d]) {
                var f = b.st[d] ? b.st[d].markup : !1;
                y("FirstMarkupParse", f), f ? b.currTemplate[d] = a(f) : b.currTemplate[d] = !0;
            }
            e && e !== c.type && b.container.removeClass("mfp-" + e + "-holder");
            var g = b["get" + d.charAt(0).toUpperCase() + d.slice(1)](c, b.currTemplate[d]);
            b.appendContent(g, d), c.preloaded = !0, y(n, c), e = c.type, b.container.prepend(b.contentContainer), 
            y("AfterChange");
        },
        appendContent: function(a, c) {
            b.content = a, a ? b.st.showCloseBtn && b.st.closeBtnInside && b.currTemplate[c] === !0 ? b.content.find(".mfp-close").length || b.content.append(z()) : b.content = a : b.content = "", 
            y(k), b.container.addClass("mfp-" + c + "-holder"), b.contentContainer.append(b.content);
        },
        parseEl: function(c) {
            var d, e = b.items[c];
            if (e.tagName ? e = {
                el: a(e)
            } : (d = e.type, e = {
                data: e,
                src: e.src
            }), e.el) {
                for (var f = b.types, g = 0; g < f.length; g++) if (e.el.hasClass("mfp-" + f[g])) {
                    d = f[g];
                    break;
                }
                e.src = e.el.attr("data-mfp-src"), e.src || (e.src = e.el.attr("href"));
            }
            return e.type = d || b.st.type || "inline", e.index = c, e.parsed = !0, b.items[c] = e, 
            y("ElementParse", e), b.items[c];
        },
        addGroup: function(a, c) {
            var d = function(d) {
                d.mfpEl = this, b._openClick(d, a, c);
            };
            c || (c = {});
            var e = "click.magnificPopup";
            c.mainEl = a, c.items ? (c.isObj = !0, a.off(e).on(e, d)) : (c.isObj = !1, c.delegate ? a.off(e).on(e, c.delegate, d) : (c.items = a, 
            a.off(e).on(e, d)));
        },
        _openClick: function(c, d, e) {
            var f = void 0 !== e.midClick ? e.midClick : a.magnificPopup.defaults.midClick;
            if (f || !(2 === c.which || c.ctrlKey || c.metaKey || c.altKey || c.shiftKey)) {
                var g = void 0 !== e.disableOn ? e.disableOn : a.magnificPopup.defaults.disableOn;
                if (g) if (a.isFunction(g)) {
                    if (!g.call(b)) return !0;
                } else if (v.width() < g) return !0;
                c.type && (c.preventDefault(), b.isOpen && c.stopPropagation()), e.el = a(c.mfpEl), 
                e.delegate && (e.items = d.find(e.delegate)), b.open(e);
            }
        },
        updateStatus: function(a, d) {
            if (b.preloader) {
                c !== a && b.container.removeClass("mfp-s-" + c), d || "loading" !== a || (d = b.st.tLoading);
                var e = {
                    status: a,
                    text: d
                };
                y("UpdateStatus", e), a = e.status, d = e.text, b.preloader.html(d), b.preloader.find("a").on("click", function(a) {
                    a.stopImmediatePropagation();
                }), b.container.addClass("mfp-s-" + a), c = a;
            }
        },
        _checkIfClose: function(c) {
            if (!a(c).hasClass(s)) {
                var d = b.st.closeOnContentClick, e = b.st.closeOnBgClick;
                if (d && e) return !0;
                if (!b.content || a(c).hasClass("mfp-close") || b.preloader && c === b.preloader[0]) return !0;
                if (c === b.content[0] || a.contains(b.content[0], c)) {
                    if (d) return !0;
                } else if (e && a.contains(document, c)) return !0;
                return !1;
            }
        },
        _addClassToMFP: function(a) {
            b.bgOverlay.addClass(a), b.wrap.addClass(a);
        },
        _removeClassFromMFP: function(a) {
            this.bgOverlay.removeClass(a), b.wrap.removeClass(a);
        },
        _hasScrollBar: function(a) {
            return (b.isIE7 ? d.height() : document.body.scrollHeight) > (a || v.height());
        },
        _setFocus: function() {
            (b.st.focus ? b.content.find(b.st.focus).eq(0) : b.wrap).focus();
        },
        _onFocusIn: function(c) {
            return c.target === b.wrap[0] || a.contains(b.wrap[0], c.target) ? void 0 : (b._setFocus(), 
            !1);
        },
        _parseMarkup: function(b, c, d) {
            var e;
            d.data && (c = a.extend(d.data, c)), y(l, [ b, c, d ]), a.each(c, function(a, c) {
                if (void 0 === c || c === !1) return !0;
                if (e = a.split("_"), e.length > 1) {
                    var d = b.find(p + "-" + e[0]);
                    if (d.length > 0) {
                        var f = e[1];
                        "replaceWith" === f ? d[0] !== c[0] && d.replaceWith(c) : "img" === f ? d.is("img") ? d.attr("src", c) : d.replaceWith('<img src="' + c + '" class="' + d.attr("class") + '" />') : d.attr(e[1], c);
                    }
                } else b.find(p + "-" + a).html(c);
            });
        },
        _getScrollbarSize: function() {
            if (void 0 === b.scrollbarSize) {
                var a = document.createElement("div");
                a.style.cssText = "width: 99px; height: 99px; overflow: scroll; position: absolute; top: -9999px;", 
                document.body.appendChild(a), b.scrollbarSize = a.offsetWidth - a.clientWidth, document.body.removeChild(a);
            }
            return b.scrollbarSize;
        }
    }, a.magnificPopup = {
        instance: null,
        proto: t.prototype,
        modules: [],
        open: function(b, c) {
            return A(), b = b ? a.extend(!0, {}, b) : {}, b.isObj = !0, b.index = c || 0, this.instance.open(b);
        },
        close: function() {
            return a.magnificPopup.instance && a.magnificPopup.instance.close();
        },
        registerModule: function(b, c) {
            c.options && (a.magnificPopup.defaults[b] = c.options), a.extend(this.proto, c.proto), 
            this.modules.push(b);
        },
        defaults: {
            disableOn: 0,
            key: null,
            midClick: !1,
            mainClass: "",
            preloader: !0,
            focus: "",
            closeOnContentClick: !1,
            closeOnBgClick: !0,
            closeBtnInside: !0,
            showCloseBtn: !0,
            enableEscapeKey: !0,
            modal: !1,
            alignTop: !1,
            removalDelay: 0,
            prependTo: null,
            fixedContentPos: "auto",
            fixedBgPos: "auto",
            overflowY: "auto",
            closeMarkup: '<button title="%title%" type="button" class="mfp-close">&#215;</button>',
            tClose: "Close (Esc)",
            tLoading: "Loading...",
            autoFocusLast: !0
        }
    }, a.fn.magnificPopup = function(c) {
        A();
        var d = a(this);
        if ("string" == typeof c) if ("open" === c) {
            var e, f = u ? d.data("magnificPopup") : d[0].magnificPopup, g = parseInt(arguments[1], 10) || 0;
            f.items ? e = f.items[g] : (e = d, f.delegate && (e = e.find(f.delegate)), e = e.eq(g)), 
            b._openClick({
                mfpEl: e
            }, d, f);
        } else b.isOpen && b[c].apply(b, Array.prototype.slice.call(arguments, 1)); else c = a.extend(!0, {}, c), 
        u ? d.data("magnificPopup", c) : d[0].magnificPopup = c, b.addGroup(d, c);
        return d;
    };
    var C, D, E, F = "inline", G = function() {
        E && (D.after(E.addClass(C)).detach(), E = null);
    };
    a.magnificPopup.registerModule(F, {
        options: {
            hiddenClass: "hide",
            markup: "",
            tNotFound: "Content not found"
        },
        proto: {
            initInline: function() {
                b.types.push(F), w(h + "." + F, function() {
                    G();
                });
            },
            getInline: function(c, d) {
                if (G(), c.src) {
                    var e = b.st.inline, f = a(c.src);
                    if (f.length) {
                        var g = f[0].parentNode;
                        g && g.tagName && (D || (C = e.hiddenClass, D = x(C), C = "mfp-" + C), E = f.after(D).detach().removeClass(C)), 
                        b.updateStatus("ready");
                    } else b.updateStatus("error", e.tNotFound), f = a("<div>");
                    return c.inlineElement = f, f;
                }
                return b.updateStatus("ready"), b._parseMarkup(d, {}, c), d;
            }
        }
    });
    var H, I = "ajax", J = function() {
        H && a(document.body).removeClass(H);
    }, K = function() {
        J(), b.req && b.req.abort();
    };
    a.magnificPopup.registerModule(I, {
        options: {
            settings: null,
            cursor: "mfp-ajax-cur",
            tError: '<a href="%url%">The content</a> could not be loaded.'
        },
        proto: {
            initAjax: function() {
                b.types.push(I), H = b.st.ajax.cursor, w(h + "." + I, K), w("BeforeChange." + I, K);
            },
            getAjax: function(c) {
                H && a(document.body).addClass(H), b.updateStatus("loading");
                var d = a.extend({
                    url: c.src,
                    success: function(d, e, f) {
                        var g = {
                            data: d,
                            xhr: f
                        };
                        y("ParseAjax", g), b.appendContent(a(g.data), I), c.finished = !0, J(), b._setFocus(), 
                        setTimeout(function() {
                            b.wrap.addClass(q);
                        }, 16), b.updateStatus("ready"), y("AjaxContentAdded");
                    },
                    error: function() {
                        J(), c.finished = c.loadError = !0, b.updateStatus("error", b.st.ajax.tError.replace("%url%", c.src));
                    }
                }, b.st.ajax.settings);
                return b.req = a.ajax(d), "";
            }
        }
    });
    var L, M = function(c) {
        if (c.data && void 0 !== c.data.title) return c.data.title;
        var d = b.st.image.titleSrc;
        if (d) {
            if (a.isFunction(d)) return d.call(b, c);
            if (c.el) return c.el.attr(d) || "";
        }
        return "";
    };
    a.magnificPopup.registerModule("image", {
        options: {
            markup: '<div class="mfp-figure"><div class="mfp-close"></div><figure><div class="mfp-img"></div><figcaption><div class="mfp-bottom-bar"><div class="mfp-title"></div><div class="mfp-counter"></div></div></figcaption></figure></div>',
            cursor: "mfp-zoom-out-cur",
            titleSrc: "title",
            verticalFit: !0,
            tError: '<a href="%url%">The image</a> could not be loaded.'
        },
        proto: {
            initImage: function() {
                var c = b.st.image, d = ".image";
                b.types.push("image"), w(m + d, function() {
                    "image" === b.currItem.type && c.cursor && a(document.body).addClass(c.cursor);
                }), w(h + d, function() {
                    c.cursor && a(document.body).removeClass(c.cursor), v.off("resize" + p);
                }), w("Resize" + d, b.resizeImage), b.isLowIE && w("AfterChange", b.resizeImage);
            },
            resizeImage: function() {
                var a = b.currItem;
                if (a && a.img && b.st.image.verticalFit) {
                    var c = 0;
                    b.isLowIE && (c = parseInt(a.img.css("padding-top"), 10) + parseInt(a.img.css("padding-bottom"), 10)), 
                    a.img.css("max-height", b.wH - c);
                }
            },
            _onImageHasSize: function(a) {
                a.img && (a.hasSize = !0, L && clearInterval(L), a.isCheckingImgSize = !1, y("ImageHasSize", a), 
                a.imgHidden && (b.content && b.content.removeClass("mfp-loading"), a.imgHidden = !1));
            },
            findImageSize: function(a) {
                var c = 0, d = a.img[0], e = function(f) {
                    L && clearInterval(L), L = setInterval(function() {
                        return d.naturalWidth > 0 ? void b._onImageHasSize(a) : (c > 200 && clearInterval(L), 
                        c++, void (3 === c ? e(10) : 40 === c ? e(50) : 100 === c && e(500)));
                    }, f);
                };
                e(1);
            },
            getImage: function(c, d) {
                var e = 0, f = function() {
                    c && (c.img[0].complete ? (c.img.off(".mfploader"), c === b.currItem && (b._onImageHasSize(c), 
                    b.updateStatus("ready")), c.hasSize = !0, c.loaded = !0, y("ImageLoadComplete")) : (e++, 
                    200 > e ? setTimeout(f, 100) : g()));
                }, g = function() {
                    c && (c.img.off(".mfploader"), c === b.currItem && (b._onImageHasSize(c), b.updateStatus("error", h.tError.replace("%url%", c.src))), 
                    c.hasSize = !0, c.loaded = !0, c.loadError = !0);
                }, h = b.st.image, i = d.find(".mfp-img");
                if (i.length) {
                    var j = document.createElement("img");
                    j.className = "mfp-img", c.el && c.el.find("img").length && (j.alt = c.el.find("img").attr("alt")), 
                    c.img = a(j).on("load.mfploader", f).on("error.mfploader", g), j.src = c.src, i.is("img") && (c.img = c.img.clone()), 
                    j = c.img[0], j.naturalWidth > 0 ? c.hasSize = !0 : j.width || (c.hasSize = !1);
                }
                return b._parseMarkup(d, {
                    title: M(c),
                    img_replaceWith: c.img
                }, c), b.resizeImage(), c.hasSize ? (L && clearInterval(L), c.loadError ? (d.addClass("mfp-loading"), 
                b.updateStatus("error", h.tError.replace("%url%", c.src))) : (d.removeClass("mfp-loading"), 
                b.updateStatus("ready")), d) : (b.updateStatus("loading"), c.loading = !0, c.hasSize || (c.imgHidden = !0, 
                d.addClass("mfp-loading"), b.findImageSize(c)), d);
            }
        }
    });
    var N, O = function() {
        return void 0 === N && (N = void 0 !== document.createElement("p").style.MozTransform), 
        N;
    };
    a.magnificPopup.registerModule("zoom", {
        options: {
            enabled: !1,
            easing: "ease-in-out",
            duration: 300,
            opener: function(a) {
                return a.is("img") ? a : a.find("img");
            }
        },
        proto: {
            initZoom: function() {
                var a, c = b.st.zoom, d = ".zoom";
                if (c.enabled && b.supportsTransition) {
                    var e, f, g = c.duration, j = function(a) {
                        var b = a.clone().removeAttr("style").removeAttr("class").addClass("mfp-animated-image"), d = "all " + c.duration / 1e3 + "s " + c.easing, e = {
                            position: "fixed",
                            zIndex: 9999,
                            left: 0,
                            top: 0,
                            "-webkit-backface-visibility": "hidden"
                        }, f = "transition";
                        return e["-webkit-" + f] = e["-moz-" + f] = e["-o-" + f] = e[f] = d, b.css(e), b;
                    }, k = function() {
                        b.content.css("visibility", "visible");
                    };
                    w("BuildControls" + d, function() {
                        if (b._allowZoom()) {
                            if (clearTimeout(e), b.content.css("visibility", "hidden"), a = b._getItemToZoom(), 
                            !a) return void k();
                            f = j(a), f.css(b._getOffset()), b.wrap.append(f), e = setTimeout(function() {
                                f.css(b._getOffset(!0)), e = setTimeout(function() {
                                    k(), setTimeout(function() {
                                        f.remove(), a = f = null, y("ZoomAnimationEnded");
                                    }, 16);
                                }, g);
                            }, 16);
                        }
                    }), w(i + d, function() {
                        if (b._allowZoom()) {
                            if (clearTimeout(e), b.st.removalDelay = g, !a) {
                                if (a = b._getItemToZoom(), !a) return;
                                f = j(a);
                            }
                            f.css(b._getOffset(!0)), b.wrap.append(f), b.content.css("visibility", "hidden"), 
                            setTimeout(function() {
                                f.css(b._getOffset());
                            }, 16);
                        }
                    }), w(h + d, function() {
                        b._allowZoom() && (k(), f && f.remove(), a = null);
                    });
                }
            },
            _allowZoom: function() {
                return "image" === b.currItem.type;
            },
            _getItemToZoom: function() {
                return b.currItem.hasSize ? b.currItem.img : !1;
            },
            _getOffset: function(c) {
                var d;
                d = c ? b.currItem.img : b.st.zoom.opener(b.currItem.el || b.currItem);
                var e = d.offset(), f = parseInt(d.css("padding-top"), 10), g = parseInt(d.css("padding-bottom"), 10);
                e.top -= a(window).scrollTop() - f;
                var h = {
                    width: d.width(),
                    height: (u ? d.innerHeight() : d[0].offsetHeight) - g - f
                };
                return O() ? h["-moz-transform"] = h.transform = "translate(" + e.left + "px," + e.top + "px)" : (h.left = e.left, 
                h.top = e.top), h;
            }
        }
    });
    var P = "iframe", Q = "//about:blank", R = function(a) {
        if (b.currTemplate[P]) {
            var c = b.currTemplate[P].find("iframe");
            c.length && (a || (c[0].src = Q), b.isIE8 && c.css("display", a ? "block" : "none"));
        }
    };
    a.magnificPopup.registerModule(P, {
        options: {
            markup: '<div class="mfp-iframe-scaler"><div class="mfp-close"></div><iframe class="mfp-iframe" src="//about:blank" frameborder="0" allowfullscreen></iframe></div>',
            srcAction: "iframe_src",
            patterns: {
                youtube: {
                    index: "youtube.com",
                    id: "v=",
                    src: "//www.youtube.com/embed/%id%?autoplay=1"
                },
                vimeo: {
                    index: "vimeo.com/",
                    id: "/",
                    src: "//player.vimeo.com/video/%id%?autoplay=1"
                },
                gmaps: {
                    index: "//maps.google.",
                    src: "%id%&output=embed"
                }
            }
        },
        proto: {
            initIframe: function() {
                b.types.push(P), w("BeforeChange", function(a, b, c) {
                    b !== c && (b === P ? R() : c === P && R(!0));
                }), w(h + "." + P, function() {
                    R();
                });
            },
            getIframe: function(c, d) {
                var e = c.src, f = b.st.iframe;
                a.each(f.patterns, function() {
                    return e.indexOf(this.index) > -1 ? (this.id && (e = "string" == typeof this.id ? e.substr(e.lastIndexOf(this.id) + this.id.length, e.length) : this.id.call(this, e)), 
                    e = this.src.replace("%id%", e), !1) : void 0;
                });
                var g = {};
                return f.srcAction && (g[f.srcAction] = e), b._parseMarkup(d, g, c), b.updateStatus("ready"), 
                d;
            }
        }
    });
    var S = function(a) {
        var c = b.items.length;
        return a > c - 1 ? a - c : 0 > a ? c + a : a;
    }, T = function(a, b, c) {
        return a.replace(/%curr%/gi, b + 1).replace(/%total%/gi, c);
    };
    a.magnificPopup.registerModule("gallery", {
        options: {
            enabled: !1,
            arrowMarkup: '<button title="%title%" type="button" class="mfp-arrow mfp-arrow-%dir%"></button>',
            preload: [ 0, 2 ],
            navigateByImgClick: !0,
            arrows: !0,
            tPrev: "Previous (Left arrow key)",
            tNext: "Next (Right arrow key)",
            tCounter: "%curr% of %total%"
        },
        proto: {
            initGallery: function() {
                var c = b.st.gallery, e = ".mfp-gallery", g = Boolean(a.fn.mfpFastClick);
                return b.direction = !0, c && c.enabled ? (f += " mfp-gallery", w(m + e, function() {
                    c.navigateByImgClick && b.wrap.on("click" + e, ".mfp-img", function() {
                        return b.items.length > 1 ? (b.next(), !1) : void 0;
                    }), d.on("keydown" + e, function(a) {
                        37 === a.keyCode ? b.prev() : 39 === a.keyCode && b.next();
                    });
                }), w("UpdateStatus" + e, function(a, c) {
                    c.text && (c.text = T(c.text, b.currItem.index, b.items.length));
                }), w(l + e, function(a, d, e, f) {
                    var g = b.items.length;
                    e.counter = g > 1 ? T(c.tCounter, f.index, g) : "";
                }), w("BuildControls" + e, function() {
                    if (b.items.length > 1 && c.arrows && !b.arrowLeft) {
                        var d = c.arrowMarkup, e = b.arrowLeft = a(d.replace(/%title%/gi, c.tPrev).replace(/%dir%/gi, "left")).addClass(s), f = b.arrowRight = a(d.replace(/%title%/gi, c.tNext).replace(/%dir%/gi, "right")).addClass(s), h = g ? "mfpFastClick" : "click";
                        e[h](function() {
                            b.prev();
                        }), f[h](function() {
                            b.next();
                        }), b.isIE7 && (x("b", e[0], !1, !0), x("a", e[0], !1, !0), x("b", f[0], !1, !0), 
                        x("a", f[0], !1, !0)), b.container.append(e.add(f));
                    }
                }), w(n + e, function() {
                    b._preloadTimeout && clearTimeout(b._preloadTimeout), b._preloadTimeout = setTimeout(function() {
                        b.preloadNearbyImages(), b._preloadTimeout = null;
                    }, 16);
                }), void w(h + e, function() {
                    d.off(e), b.wrap.off("click" + e), b.arrowLeft && g && b.arrowLeft.add(b.arrowRight).destroyMfpFastClick(), 
                    b.arrowRight = b.arrowLeft = null;
                })) : !1;
            },
            next: function() {
                b.direction = !0, b.index = S(b.index + 1), b.updateItemHTML();
            },
            prev: function() {
                b.direction = !1, b.index = S(b.index - 1), b.updateItemHTML();
            },
            goTo: function(a) {
                b.direction = a >= b.index, b.index = a, b.updateItemHTML();
            },
            preloadNearbyImages: function() {
                var a, c = b.st.gallery.preload, d = Math.min(c[0], b.items.length), e = Math.min(c[1], b.items.length);
                for (a = 1; a <= (b.direction ? e : d); a++) b._preloadItem(b.index + a);
                for (a = 1; a <= (b.direction ? d : e); a++) b._preloadItem(b.index - a);
            },
            _preloadItem: function(c) {
                if (c = S(c), !b.items[c].preloaded) {
                    var d = b.items[c];
                    d.parsed || (d = b.parseEl(c)), y("LazyLoad", d), "image" === d.type && (d.img = a('<img class="mfp-img" />').on("load.mfploader", function() {
                        d.hasSize = !0;
                    }).on("error.mfploader", function() {
                        d.hasSize = !0, d.loadError = !0, y("LazyLoadError", d);
                    }).attr("src", d.src)), d.preloaded = !0;
                }
            }
        }
    });
    var U = "retina";
    a.magnificPopup.registerModule(U, {
        options: {
            replaceSrc: function(a) {
                return a.src.replace(/\.\w+$/, function(a) {
                    return "@2x" + a;
                });
            },
            ratio: 1
        },
        proto: {
            initRetina: function() {
                if (window.devicePixelRatio > 1) {
                    var a = b.st.retina, c = a.ratio;
                    c = isNaN(c) ? c() : c, c > 1 && (w("ImageHasSize." + U, function(a, b) {
                        b.img.css({
                            "max-width": b.img[0].naturalWidth / c,
                            width: "100%"
                        });
                    }), w("ElementParse." + U, function(b, d) {
                        d.src = a.replaceSrc(d, c);
                    }));
                }
            }
        }
    }), function() {
        var b = 1e3, c = "ontouchstart" in window, d = function() {
            v.off("touchmove" + f + " touchend" + f);
        }, e = "mfpFastClick", f = "." + e;
        a.fn.mfpFastClick = function(e) {
            return a(this).each(function() {
                var g, h = a(this);
                if (c) {
                    var i, j, k, l, m, n;
                    h.on("touchstart" + f, function(a) {
                        l = !1, n = 1, m = a.originalEvent ? a.originalEvent.touches[0] : a.touches[0], 
                        j = m.clientX, k = m.clientY, v.on("touchmove" + f, function(a) {
                            m = a.originalEvent ? a.originalEvent.touches : a.touches, n = m.length, m = m[0], 
                            (Math.abs(m.clientX - j) > 10 || Math.abs(m.clientY - k) > 10) && (l = !0, d());
                        }).on("touchend" + f, function(a) {
                            d(), l || n > 1 || (g = !0, a.preventDefault(), clearTimeout(i), i = setTimeout(function() {
                                g = !1;
                            }, b), e());
                        });
                    });
                }
                h.on("click" + f, function() {
                    g || e();
                });
            });
        }, a.fn.destroyMfpFastClick = function() {
            a(this).off("touchstart" + f + " click" + f), c && v.off("touchmove" + f + " touchend" + f);
        };
    }(), A();
});

/*
 * jQuery FlexSlider v2.6.3
 * Copyright 2012 WooThemes
 * Contributing Author: Tyler Smith
 */
!function($) {
    var e = !0;
    $.flexslider = function(t, a) {
        var n = $(t);
        n.vars = $.extend({}, $.flexslider.defaults, a);
        var i = n.vars.namespace, s = window.navigator && window.navigator.msPointerEnabled && window.MSGesture, r = ("ontouchstart" in window || s || window.DocumentTouch && document instanceof DocumentTouch) && n.vars.touch, o = "click touchend MSPointerUp keyup", l = "", c, d = "vertical" === n.vars.direction, u = n.vars.reverse, v = n.vars.itemWidth > 0, p = "fade" === n.vars.animation, m = "" !== n.vars.asNavFor, f = {};
        $.data(t, "flexslider", n), f = {
            init: function() {
                n.animating = !1, n.currentSlide = parseInt(n.vars.startAt ? n.vars.startAt : 0, 10), 
                isNaN(n.currentSlide) && (n.currentSlide = 0), n.animatingTo = n.currentSlide, n.atEnd = 0 === n.currentSlide || n.currentSlide === n.last, 
                n.containerSelector = n.vars.selector.substr(0, n.vars.selector.search(" ")), n.slides = $(n.vars.selector, n), 
                n.container = $(n.containerSelector, n), n.count = n.slides.length, n.syncExists = $(n.vars.sync).length > 0, 
                "slide" === n.vars.animation && (n.vars.animation = "swing"), n.prop = d ? "top" : "marginLeft", 
                n.args = {}, n.manualPause = !1, n.stopped = !1, n.started = !1, n.startTimeout = null, 
                n.transitions = !n.vars.video && !p && n.vars.useCSS && function() {
                    var e = document.createElement("div"), t = [ "perspectiveProperty", "WebkitPerspective", "MozPerspective", "OPerspective", "msPerspective" ];
                    for (var a in t) if (void 0 !== e.style[t[a]]) return n.pfx = t[a].replace("Perspective", "").toLowerCase(), 
                    n.prop = "-" + n.pfx + "-transform", !0;
                    return !1;
                }(), n.ensureAnimationEnd = "", "" !== n.vars.controlsContainer && (n.controlsContainer = $(n.vars.controlsContainer).length > 0 && $(n.vars.controlsContainer)), 
                "" !== n.vars.manualControls && (n.manualControls = $(n.vars.manualControls).length > 0 && $(n.vars.manualControls)), 
                "" !== n.vars.customDirectionNav && (n.customDirectionNav = 2 === $(n.vars.customDirectionNav).length && $(n.vars.customDirectionNav)), 
                n.vars.randomize && (n.slides.sort(function() {
                    return Math.round(Math.random()) - .5;
                }), n.container.empty().append(n.slides)), n.doMath(), n.setup("init"), n.vars.controlNav && f.controlNav.setup(), 
                n.vars.directionNav && f.directionNav.setup(), n.vars.keyboard && (1 === $(n.containerSelector).length || n.vars.multipleKeyboard) && $(document).bind("keyup", function(e) {
                    var t = e.keyCode;
                    if (!n.animating && (39 === t || 37 === t)) {
                        var a = 39 === t ? n.getTarget("next") : 37 === t ? n.getTarget("prev") : !1;
                        n.flexAnimate(a, n.vars.pauseOnAction);
                    }
                }), n.vars.mousewheel && n.bind("mousewheel", function(e, t, a, i) {
                    e.preventDefault();
                    var s = 0 > t ? n.getTarget("next") : n.getTarget("prev");
                    n.flexAnimate(s, n.vars.pauseOnAction);
                }), n.vars.pausePlay && f.pausePlay.setup(), n.vars.slideshow && n.vars.pauseInvisible && f.pauseInvisible.init(), 
                n.vars.slideshow && (n.vars.pauseOnHover && n.hover(function() {
                    n.manualPlay || n.manualPause || n.pause();
                }, function() {
                    n.manualPause || n.manualPlay || n.stopped || n.play();
                }), n.vars.pauseInvisible && f.pauseInvisible.isHidden() || (n.vars.initDelay > 0 ? n.startTimeout = setTimeout(n.play, n.vars.initDelay) : n.play())), 
                m && f.asNav.setup(), r && n.vars.touch && f.touch(), (!p || p && n.vars.smoothHeight) && $(window).bind("resize orientationchange focus", f.resize), 
                n.find("img").attr("draggable", "false"), setTimeout(function() {
                    n.vars.start(n);
                }, 200);
            },
            asNav: {
                setup: function() {
                    n.asNav = !0, n.animatingTo = Math.floor(n.currentSlide / n.move), n.currentItem = n.currentSlide, 
                    n.slides.removeClass(i + "active-slide").eq(n.currentItem).addClass(i + "active-slide"), 
                    s ? (t._slider = n, n.slides.each(function() {
                        var e = this;
                        e._gesture = new MSGesture(), e._gesture.target = e, e.addEventListener("MSPointerDown", function(e) {
                            e.preventDefault(), e.currentTarget._gesture && e.currentTarget._gesture.addPointer(e.pointerId);
                        }, !1), e.addEventListener("MSGestureTap", function(e) {
                            e.preventDefault();
                            var t = $(this), a = t.index();
                            $(n.vars.asNavFor).data("flexslider").animating || t.hasClass("active") || (n.direction = n.currentItem < a ? "next" : "prev", 
                            n.flexAnimate(a, n.vars.pauseOnAction, !1, !0, !0));
                        });
                    })) : n.slides.on(o, function(e) {
                        e.preventDefault();
                        var t = $(this), a = t.index(), s = t.offset().left - $(n).scrollLeft();
                        0 >= s && t.hasClass(i + "active-slide") ? n.flexAnimate(n.getTarget("prev"), !0) : $(n.vars.asNavFor).data("flexslider").animating || t.hasClass(i + "active-slide") || (n.direction = n.currentItem < a ? "next" : "prev", 
                        n.flexAnimate(a, n.vars.pauseOnAction, !1, !0, !0));
                    });
                }
            },
            controlNav: {
                setup: function() {
                    n.manualControls ? f.controlNav.setupManual() : f.controlNav.setupPaging();
                },
                setupPaging: function() {
                    var e = "thumbnails" === n.vars.controlNav ? "control-thumbs" : "control-paging", t = 1, a, s;
                    if (n.controlNavScaffold = $('<ol class="' + i + "control-nav " + i + e + '"></ol>'), 
                    n.pagingCount > 1) for (var r = 0; r < n.pagingCount; r++) {
                        s = n.slides.eq(r), void 0 === s.attr("data-thumb-alt") && s.attr("data-thumb-alt", "");
                        var c = "" !== s.attr("data-thumb-alt") ? c = ' alt="' + s.attr("data-thumb-alt") + '"' : "";
                        if (a = "thumbnails" === n.vars.controlNav ? '<img src="' + s.attr("data-thumb") + '"' + c + "/>" : '<a href="#">' + t + "</a>", 
                        "thumbnails" === n.vars.controlNav && !0 === n.vars.thumbCaptions) {
                            var d = s.attr("data-thumbcaption");
                            "" !== d && void 0 !== d && (a += '<span class="' + i + 'caption">' + d + "</span>");
                        }
                        n.controlNavScaffold.append("<li>" + a + "</li>"), t++;
                    }
                    n.controlsContainer ? $(n.controlsContainer).append(n.controlNavScaffold) : n.append(n.controlNavScaffold), 
                    f.controlNav.set(), f.controlNav.active(), n.controlNavScaffold.delegate("a, img", o, function(e) {
                        if (e.preventDefault(), "" === l || l === e.type) {
                            var t = $(this), a = n.controlNav.index(t);
                            t.hasClass(i + "active") || (n.direction = a > n.currentSlide ? "next" : "prev", 
                            n.flexAnimate(a, n.vars.pauseOnAction));
                        }
                        "" === l && (l = e.type), f.setToClearWatchedEvent();
                    });
                },
                setupManual: function() {
                    n.controlNav = n.manualControls, f.controlNav.active(), n.controlNav.bind(o, function(e) {
                        if (e.preventDefault(), "" === l || l === e.type) {
                            var t = $(this), a = n.controlNav.index(t);
                            t.hasClass(i + "active") || (a > n.currentSlide ? n.direction = "next" : n.direction = "prev", 
                            n.flexAnimate(a, n.vars.pauseOnAction));
                        }
                        "" === l && (l = e.type), f.setToClearWatchedEvent();
                    });
                },
                set: function() {
                    var e = "thumbnails" === n.vars.controlNav ? "img" : "a";
                    n.controlNav = $("." + i + "control-nav li " + e, n.controlsContainer ? n.controlsContainer : n);
                },
                active: function() {
                    n.controlNav.removeClass(i + "active").eq(n.animatingTo).addClass(i + "active");
                },
                update: function(e, t) {
                    n.pagingCount > 1 && "add" === e ? n.controlNavScaffold.append($('<li><a href="#">' + n.count + "</a></li>")) : 1 === n.pagingCount ? n.controlNavScaffold.find("li").remove() : n.controlNav.eq(t).closest("li").remove(), 
                    f.controlNav.set(), n.pagingCount > 1 && n.pagingCount !== n.controlNav.length ? n.update(t, e) : f.controlNav.active();
                }
            },
            directionNav: {
                setup: function() {
                    var e = $('<ul class="' + i + 'direction-nav"><li class="' + i + 'nav-prev"><a class="' + i + 'prev" href="#">' + n.vars.prevText + '</a></li><li class="' + i + 'nav-next"><a class="' + i + 'next" href="#">' + n.vars.nextText + "</a></li></ul>");
                    n.customDirectionNav ? n.directionNav = n.customDirectionNav : n.controlsContainer ? ($(n.controlsContainer).append(e), 
                    n.directionNav = $("." + i + "direction-nav li a", n.controlsContainer)) : (n.append(e), 
                    n.directionNav = $("." + i + "direction-nav li a", n)), f.directionNav.update(), 
                    n.directionNav.bind(o, function(e) {
                        e.preventDefault();
                        var t;
                        "" !== l && l !== e.type || (t = $(this).hasClass(i + "next") ? n.getTarget("next") : n.getTarget("prev"), 
                        n.flexAnimate(t, n.vars.pauseOnAction)), "" === l && (l = e.type), f.setToClearWatchedEvent();
                    });
                },
                update: function() {
                    var e = i + "disabled";
                    1 === n.pagingCount ? n.directionNav.addClass(e).attr("tabindex", "-1") : n.vars.animationLoop ? n.directionNav.removeClass(e).removeAttr("tabindex") : 0 === n.animatingTo ? n.directionNav.removeClass(e).filter("." + i + "prev").addClass(e).attr("tabindex", "-1") : n.animatingTo === n.last ? n.directionNav.removeClass(e).filter("." + i + "next").addClass(e).attr("tabindex", "-1") : n.directionNav.removeClass(e).removeAttr("tabindex");
                }
            },
            pausePlay: {
                setup: function() {
                    var e = $('<div class="' + i + 'pauseplay"><a href="#"></a></div>');
                    n.controlsContainer ? (n.controlsContainer.append(e), n.pausePlay = $("." + i + "pauseplay a", n.controlsContainer)) : (n.append(e), 
                    n.pausePlay = $("." + i + "pauseplay a", n)), f.pausePlay.update(n.vars.slideshow ? i + "pause" : i + "play"), 
                    n.pausePlay.bind(o, function(e) {
                        e.preventDefault(), "" !== l && l !== e.type || ($(this).hasClass(i + "pause") ? (n.manualPause = !0, 
                        n.manualPlay = !1, n.pause()) : (n.manualPause = !1, n.manualPlay = !0, n.play())), 
                        "" === l && (l = e.type), f.setToClearWatchedEvent();
                    });
                },
                update: function(e) {
                    "play" === e ? n.pausePlay.removeClass(i + "pause").addClass(i + "play").html(n.vars.playText) : n.pausePlay.removeClass(i + "play").addClass(i + "pause").html(n.vars.pauseText);
                }
            },
            touch: function() {
                function e(e) {
                    e.stopPropagation(), n.animating ? e.preventDefault() : (n.pause(), t._gesture.addPointer(e.pointerId), 
                    T = 0, c = d ? n.h : n.w, f = Number(new Date()), l = v && u && n.animatingTo === n.last ? 0 : v && u ? n.limit - (n.itemW + n.vars.itemMargin) * n.move * n.animatingTo : v && n.currentSlide === n.last ? n.limit : v ? (n.itemW + n.vars.itemMargin) * n.move * n.currentSlide : u ? (n.last - n.currentSlide + n.cloneOffset) * c : (n.currentSlide + n.cloneOffset) * c);
                }
                function a(e) {
                    e.stopPropagation();
                    var a = e.target._slider;
                    if (a) {
                        var n = -e.translationX, i = -e.translationY;
                        return T += d ? i : n, m = T, y = d ? Math.abs(T) < Math.abs(-n) : Math.abs(T) < Math.abs(-i), 
                        e.detail === e.MSGESTURE_FLAG_INERTIA ? void setImmediate(function() {
                            t._gesture.stop();
                        }) : void ((!y || Number(new Date()) - f > 500) && (e.preventDefault(), !p && a.transitions && (a.vars.animationLoop || (m = T / (0 === a.currentSlide && 0 > T || a.currentSlide === a.last && T > 0 ? Math.abs(T) / c + 2 : 1)), 
                        a.setProps(l + m, "setTouch"))));
                    }
                }
                function i(e) {
                    e.stopPropagation();
                    var t = e.target._slider;
                    if (t) {
                        if (t.animatingTo === t.currentSlide && !y && null !== m) {
                            var a = u ? -m : m, n = a > 0 ? t.getTarget("next") : t.getTarget("prev");
                            t.canAdvance(n) && (Number(new Date()) - f < 550 && Math.abs(a) > 50 || Math.abs(a) > c / 2) ? t.flexAnimate(n, t.vars.pauseOnAction) : p || t.flexAnimate(t.currentSlide, t.vars.pauseOnAction, !0);
                        }
                        r = null, o = null, m = null, l = null, T = 0;
                    }
                }
                var r, o, l, c, m, f, g, h, S, y = !1, x = 0, b = 0, T = 0;
                s ? (t.style.msTouchAction = "none", t._gesture = new MSGesture(), t._gesture.target = t, 
                t.addEventListener("MSPointerDown", e, !1), t._slider = n, t.addEventListener("MSGestureChange", a, !1), 
                t.addEventListener("MSGestureEnd", i, !1)) : (g = function(e) {
                    n.animating ? e.preventDefault() : (window.navigator.msPointerEnabled || 1 === e.touches.length) && (n.pause(), 
                    c = d ? n.h : n.w, f = Number(new Date()), x = e.touches[0].pageX, b = e.touches[0].pageY, 
                    l = v && u && n.animatingTo === n.last ? 0 : v && u ? n.limit - (n.itemW + n.vars.itemMargin) * n.move * n.animatingTo : v && n.currentSlide === n.last ? n.limit : v ? (n.itemW + n.vars.itemMargin) * n.move * n.currentSlide : u ? (n.last - n.currentSlide + n.cloneOffset) * c : (n.currentSlide + n.cloneOffset) * c, 
                    r = d ? b : x, o = d ? x : b, t.addEventListener("touchmove", h, !1), t.addEventListener("touchend", S, !1));
                }, h = function(e) {
                    x = e.touches[0].pageX, b = e.touches[0].pageY, m = d ? r - b : r - x, y = d ? Math.abs(m) < Math.abs(x - o) : Math.abs(m) < Math.abs(b - o);
                    var t = 500;
                    (!y || Number(new Date()) - f > t) && (e.preventDefault(), !p && n.transitions && (n.vars.animationLoop || (m /= 0 === n.currentSlide && 0 > m || n.currentSlide === n.last && m > 0 ? Math.abs(m) / c + 2 : 1), 
                    n.setProps(l + m, "setTouch")));
                }, S = function(e) {
                    if (t.removeEventListener("touchmove", h, !1), n.animatingTo === n.currentSlide && !y && null !== m) {
                        var a = u ? -m : m, i = a > 0 ? n.getTarget("next") : n.getTarget("prev");
                        n.canAdvance(i) && (Number(new Date()) - f < 550 && Math.abs(a) > 50 || Math.abs(a) > c / 2) ? n.flexAnimate(i, n.vars.pauseOnAction) : p || n.flexAnimate(n.currentSlide, n.vars.pauseOnAction, !0);
                    }
                    t.removeEventListener("touchend", S, !1), r = null, o = null, m = null, l = null;
                }, t.addEventListener("touchstart", g, !1));
            },
            resize: function() {
                !n.animating && n.is(":visible") && (v || n.doMath(), p ? f.smoothHeight() : v ? (n.slides.width(n.computedW), 
                n.update(n.pagingCount), n.setProps()) : d ? (n.viewport.height(n.h), n.setProps(n.h, "setTotal")) : (n.vars.smoothHeight && f.smoothHeight(), 
                n.newSlides.width(n.computedW), n.setProps(n.computedW, "setTotal")));
            },
            smoothHeight: function(e) {
                if (!d || p) {
                    var t = p ? n : n.viewport;
                    e ? t.animate({
                        height: n.slides.eq(n.animatingTo).innerHeight()
                    }, e) : t.innerHeight(n.slides.eq(n.animatingTo).innerHeight());
                }
            },
            sync: function(e) {
                var t = $(n.vars.sync).data("flexslider"), a = n.animatingTo;
                switch (e) {
                  case "animate":
                    t.flexAnimate(a, n.vars.pauseOnAction, !1, !0);
                    break;

                  case "play":
                    t.playing || t.asNav || t.play();
                    break;

                  case "pause":
                    t.pause();
                }
            },
            uniqueID: function(e) {
                return e.filter("[id]").add(e.find("[id]")).each(function() {
                    var e = $(this);
                    e.attr("id", e.attr("id") + "_clone");
                }), e;
            },
            pauseInvisible: {
                visProp: null,
                init: function() {
                    var e = f.pauseInvisible.getHiddenProp();
                    if (e) {
                        var t = e.replace(/[H|h]idden/, "") + "visibilitychange";
                        document.addEventListener(t, function() {
                            f.pauseInvisible.isHidden() ? n.startTimeout ? clearTimeout(n.startTimeout) : n.pause() : n.started ? n.play() : n.vars.initDelay > 0 ? setTimeout(n.play, n.vars.initDelay) : n.play();
                        });
                    }
                },
                isHidden: function() {
                    var e = f.pauseInvisible.getHiddenProp();
                    return e ? document[e] : !1;
                },
                getHiddenProp: function() {
                    var e = [ "webkit", "moz", "ms", "o" ];
                    if ("hidden" in document) return "hidden";
                    for (var t = 0; t < e.length; t++) if (e[t] + "Hidden" in document) return e[t] + "Hidden";
                    return null;
                }
            },
            setToClearWatchedEvent: function() {
                clearTimeout(c), c = setTimeout(function() {
                    l = "";
                }, 3e3);
            }
        }, n.flexAnimate = function(e, t, a, s, o) {
            if (n.vars.animationLoop || e === n.currentSlide || (n.direction = e > n.currentSlide ? "next" : "prev"), 
            m && 1 === n.pagingCount && (n.direction = n.currentItem < e ? "next" : "prev"), 
            !n.animating && (n.canAdvance(e, o) || a) && n.is(":visible")) {
                if (m && s) {
                    var l = $(n.vars.asNavFor).data("flexslider");
                    if (n.atEnd = 0 === e || e === n.count - 1, l.flexAnimate(e, !0, !1, !0, o), n.direction = n.currentItem < e ? "next" : "prev", 
                    l.direction = n.direction, Math.ceil((e + 1) / n.visible) - 1 === n.currentSlide || 0 === e) return n.currentItem = e, 
                    n.slides.removeClass(i + "active-slide").eq(e).addClass(i + "active-slide"), !1;
                    n.currentItem = e, n.slides.removeClass(i + "active-slide").eq(e).addClass(i + "active-slide"), 
                    e = Math.floor(e / n.visible);
                }
                if (n.animating = !0, n.animatingTo = e, t && n.pause(), n.vars.before(n), n.syncExists && !o && f.sync("animate"), 
                n.vars.controlNav && f.controlNav.active(), v || n.slides.removeClass(i + "active-slide").eq(e).addClass(i + "active-slide"), 
                n.atEnd = 0 === e || e === n.last, n.vars.directionNav && f.directionNav.update(), 
                e === n.last && (n.vars.end(n), n.vars.animationLoop || n.pause()), p) r ? (n.slides.eq(n.currentSlide).css({
                    opacity: 0,
                    zIndex: 1
                }), n.slides.eq(e).css({
                    opacity: 1,
                    zIndex: 2
                }), n.wrapup(c)) : (n.slides.eq(n.currentSlide).css({
                    zIndex: 1
                }).animate({
                    opacity: 0
                }, n.vars.animationSpeed, n.vars.easing), n.slides.eq(e).css({
                    zIndex: 2
                }).animate({
                    opacity: 1
                }, n.vars.animationSpeed, n.vars.easing, n.wrapup)); else {
                    var c = d ? n.slides.filter(":first").height() : n.computedW, g, h, S;
                    v ? (g = n.vars.itemMargin, S = (n.itemW + g) * n.move * n.animatingTo, h = S > n.limit && 1 !== n.visible ? n.limit : S) : h = 0 === n.currentSlide && e === n.count - 1 && n.vars.animationLoop && "next" !== n.direction ? u ? (n.count + n.cloneOffset) * c : 0 : n.currentSlide === n.last && 0 === e && n.vars.animationLoop && "prev" !== n.direction ? u ? 0 : (n.count + 1) * c : u ? (n.count - 1 - e + n.cloneOffset) * c : (e + n.cloneOffset) * c, 
                    n.setProps(h, "", n.vars.animationSpeed), n.transitions ? (n.vars.animationLoop && n.atEnd || (n.animating = !1, 
                    n.currentSlide = n.animatingTo), n.container.unbind("webkitTransitionEnd transitionend"), 
                    n.container.bind("webkitTransitionEnd transitionend", function() {
                        clearTimeout(n.ensureAnimationEnd), n.wrapup(c);
                    }), clearTimeout(n.ensureAnimationEnd), n.ensureAnimationEnd = setTimeout(function() {
                        n.wrapup(c);
                    }, n.vars.animationSpeed + 100)) : n.container.animate(n.args, n.vars.animationSpeed, n.vars.easing, function() {
                        n.wrapup(c);
                    });
                }
                n.vars.smoothHeight && f.smoothHeight(n.vars.animationSpeed);
            }
        }, n.wrapup = function(e) {
            p || v || (0 === n.currentSlide && n.animatingTo === n.last && n.vars.animationLoop ? n.setProps(e, "jumpEnd") : n.currentSlide === n.last && 0 === n.animatingTo && n.vars.animationLoop && n.setProps(e, "jumpStart")), 
            n.animating = !1, n.currentSlide = n.animatingTo, n.vars.after(n);
        }, n.animateSlides = function() {
            !n.animating && e && n.flexAnimate(n.getTarget("next"));
        }, n.pause = function() {
            clearInterval(n.animatedSlides), n.animatedSlides = null, n.playing = !1, n.vars.pausePlay && f.pausePlay.update("play"), 
            n.syncExists && f.sync("pause");
        }, n.play = function() {
            n.playing && clearInterval(n.animatedSlides), n.animatedSlides = n.animatedSlides || setInterval(n.animateSlides, n.vars.slideshowSpeed), 
            n.started = n.playing = !0, n.vars.pausePlay && f.pausePlay.update("pause"), n.syncExists && f.sync("play");
        }, n.stop = function() {
            n.pause(), n.stopped = !0;
        }, n.canAdvance = function(e, t) {
            var a = m ? n.pagingCount - 1 : n.last;
            return t ? !0 : m && n.currentItem === n.count - 1 && 0 === e && "prev" === n.direction ? !0 : m && 0 === n.currentItem && e === n.pagingCount - 1 && "next" !== n.direction ? !1 : e !== n.currentSlide || m ? n.vars.animationLoop ? !0 : n.atEnd && 0 === n.currentSlide && e === a && "next" !== n.direction ? !1 : !n.atEnd || n.currentSlide !== a || 0 !== e || "next" !== n.direction : !1;
        }, n.getTarget = function(e) {
            return n.direction = e, "next" === e ? n.currentSlide === n.last ? 0 : n.currentSlide + 1 : 0 === n.currentSlide ? n.last : n.currentSlide - 1;
        }, n.setProps = function(e, t, a) {
            var i = function() {
                var a = e ? e : (n.itemW + n.vars.itemMargin) * n.move * n.animatingTo, i = function() {
                    if (v) return "setTouch" === t ? e : u && n.animatingTo === n.last ? 0 : u ? n.limit - (n.itemW + n.vars.itemMargin) * n.move * n.animatingTo : n.animatingTo === n.last ? n.limit : a;
                    switch (t) {
                      case "setTotal":
                        return u ? (n.count - 1 - n.currentSlide + n.cloneOffset) * e : (n.currentSlide + n.cloneOffset) * e;

                      case "setTouch":
                        return u ? e : e;

                      case "jumpEnd":
                        return u ? e : n.count * e;

                      case "jumpStart":
                        return u ? n.count * e : e;

                      default:
                        return e;
                    }
                }();
                return -1 * i + "px";
            }();
            n.transitions && (i = d ? "translate3d(0," + i + ",0)" : "translate3d(" + i + ",0,0)", 
            a = void 0 !== a ? a / 1e3 + "s" : "0s", n.container.css("-" + n.pfx + "-transition-duration", a), 
            n.container.css("transition-duration", a)), n.args[n.prop] = i, (n.transitions || void 0 === a) && n.container.css(n.args), 
            n.container.css("transform", i);
        }, n.setup = function(e) {
            if (p) n.slides.css({
                width: "100%",
                float: "left",
                marginRight: "-100%",
                position: "relative"
            }), "init" === e && (r ? n.slides.css({
                opacity: 0,
                display: "block",
                webkitTransition: "opacity " + n.vars.animationSpeed / 1e3 + "s ease",
                zIndex: 1
            }).eq(n.currentSlide).css({
                opacity: 1,
                zIndex: 2
            }) : 0 == n.vars.fadeFirstSlide ? n.slides.css({
                opacity: 0,
                display: "block",
                zIndex: 1
            }).eq(n.currentSlide).css({
                zIndex: 2
            }).css({
                opacity: 1
            }) : n.slides.css({
                opacity: 0,
                display: "block",
                zIndex: 1
            }).eq(n.currentSlide).css({
                zIndex: 2
            }).animate({
                opacity: 1
            }, n.vars.animationSpeed, n.vars.easing)), n.vars.smoothHeight && f.smoothHeight(); else {
                var t, a;
                "init" === e && (n.viewport = $('<div class="' + i + 'viewport"></div>').css({
                    overflow: "hidden",
                    position: "relative"
                }).appendTo(n).append(n.container), n.cloneCount = 0, n.cloneOffset = 0, u && (a = $.makeArray(n.slides).reverse(), 
                n.slides = $(a), n.container.empty().append(n.slides))), n.vars.animationLoop && !v && (n.cloneCount = 2, 
                n.cloneOffset = 1, "init" !== e && n.container.find(".clone").remove(), n.container.append(f.uniqueID(n.slides.first().clone().addClass("clone")).attr("aria-hidden", "true")).prepend(f.uniqueID(n.slides.last().clone().addClass("clone")).attr("aria-hidden", "true"))), 
                n.newSlides = $(n.vars.selector, n), t = u ? n.count - 1 - n.currentSlide + n.cloneOffset : n.currentSlide + n.cloneOffset, 
                d && !v ? (n.container.height(200 * (n.count + n.cloneCount) + "%").css("position", "absolute").width("100%"), 
                setTimeout(function() {
                    n.newSlides.css({
                        display: "block"
                    }), n.doMath(), n.viewport.height(n.h), n.setProps(t * n.h, "init");
                }, "init" === e ? 100 : 0)) : (n.container.width(200 * (n.count + n.cloneCount) + "%"), 
                n.setProps(t * n.computedW, "init"), setTimeout(function() {
                    n.doMath(), n.newSlides.css({
                        width: n.computedW,
                        marginRight: n.computedM,
                        float: "left",
                        display: "block"
                    }), n.vars.smoothHeight && f.smoothHeight();
                }, "init" === e ? 100 : 0));
            }
            v || n.slides.removeClass(i + "active-slide").eq(n.currentSlide).addClass(i + "active-slide"), 
            n.vars.init(n);
        }, n.doMath = function() {
            var e = n.slides.first(), t = n.vars.itemMargin, a = n.vars.minItems, i = n.vars.maxItems;
            n.w = void 0 === n.viewport ? n.width() : n.viewport.width(), n.h = e.height(), 
            n.boxPadding = e.outerWidth() - e.width(), v ? (n.itemT = n.vars.itemWidth + t, 
            n.itemM = t, n.minW = a ? a * n.itemT : n.w, n.maxW = i ? i * n.itemT - t : n.w, 
            n.itemW = n.minW > n.w ? (n.w - t * (a - 1)) / a : n.maxW < n.w ? (n.w - t * (i - 1)) / i : n.vars.itemWidth > n.w ? n.w : n.vars.itemWidth, 
            n.visible = Math.floor(n.w / n.itemW), n.move = n.vars.move > 0 && n.vars.move < n.visible ? n.vars.move : n.visible, 
            n.pagingCount = Math.ceil((n.count - n.visible) / n.move + 1), n.last = n.pagingCount - 1, 
            n.limit = 1 === n.pagingCount ? 0 : n.vars.itemWidth > n.w ? n.itemW * (n.count - 1) + t * (n.count - 1) : (n.itemW + t) * n.count - n.w - t) : (n.itemW = n.w, 
            n.itemM = t, n.pagingCount = n.count, n.last = n.count - 1), n.computedW = n.itemW - n.boxPadding, 
            n.computedM = n.itemM;
        }, n.update = function(e, t) {
            n.doMath(), v || (e < n.currentSlide ? n.currentSlide += 1 : e <= n.currentSlide && 0 !== e && (n.currentSlide -= 1), 
            n.animatingTo = n.currentSlide), n.vars.controlNav && !n.manualControls && ("add" === t && !v || n.pagingCount > n.controlNav.length ? f.controlNav.update("add") : ("remove" === t && !v || n.pagingCount < n.controlNav.length) && (v && n.currentSlide > n.last && (n.currentSlide -= 1, 
            n.animatingTo -= 1), f.controlNav.update("remove", n.last))), n.vars.directionNav && f.directionNav.update();
        }, n.addSlide = function(e, t) {
            var a = $(e);
            n.count += 1, n.last = n.count - 1, d && u ? void 0 !== t ? n.slides.eq(n.count - t).after(a) : n.container.prepend(a) : void 0 !== t ? n.slides.eq(t).before(a) : n.container.append(a), 
            n.update(t, "add"), n.slides = $(n.vars.selector + ":not(.clone)", n), n.setup(), 
            n.vars.added(n);
        }, n.removeSlide = function(e) {
            var t = isNaN(e) ? n.slides.index($(e)) : e;
            n.count -= 1, n.last = n.count - 1, isNaN(e) ? $(e, n.slides).remove() : d && u ? n.slides.eq(n.last).remove() : n.slides.eq(e).remove(), 
            n.doMath(), n.update(t, "remove"), n.slides = $(n.vars.selector + ":not(.clone)", n), 
            n.setup(), n.vars.removed(n);
        }, f.init();
    }, $(window).blur(function(t) {
        e = !1;
    }).focus(function(t) {
        e = !0;
    }), $.flexslider.defaults = {
        namespace: "flex-",
        selector: ".slides > li",
        animation: "fade",
        easing: "swing",
        direction: "horizontal",
        reverse: !1,
        animationLoop: !0,
        smoothHeight: !1,
        startAt: 0,
        slideshow: !0,
        slideshowSpeed: 7e3,
        animationSpeed: 600,
        initDelay: 0,
        randomize: !1,
        fadeFirstSlide: !0,
        thumbCaptions: !1,
        pauseOnAction: !0,
        pauseOnHover: !1,
        pauseInvisible: !0,
        useCSS: !0,
        touch: !0,
        video: !1,
        controlNav: !0,
        directionNav: !0,
        prevText: "Previous",
        nextText: "Next",
        keyboard: !0,
        multipleKeyboard: !1,
        mousewheel: !1,
        pausePlay: !1,
        pauseText: "Pause",
        playText: "Play",
        controlsContainer: "",
        manualControls: "",
        customDirectionNav: "",
        sync: "",
        asNavFor: "",
        itemWidth: 0,
        itemMargin: 0,
        minItems: 1,
        maxItems: 0,
        move: 0,
        allowOneSlide: !0,
        start: function() {},
        before: function() {},
        after: function() {},
        end: function() {},
        added: function() {},
        removed: function() {},
        init: function() {}
    }, $.fn.flexslider = function(e) {
        if (void 0 === e && (e = {}), "object" == typeof e) return this.each(function() {
            var t = $(this), a = e.selector ? e.selector : ".slides > li", n = t.find(a);
            1 === n.length && e.allowOneSlide === !1 || 0 === n.length ? (n.fadeIn(400), e.start && e.start(t)) : void 0 === t.data("flexslider") && new $.flexslider(this, e);
        });
        var t = $(this).data("flexslider");
        switch (e) {
          case "play":
            t.play();
            break;

          case "pause":
            t.pause();
            break;

          case "stop":
            t.stop();
            break;

          case "next":
            t.flexAnimate(t.getTarget("next"), !0);
            break;

          case "prev":
          case "previous":
            t.flexAnimate(t.getTarget("prev"), !0);
            break;

          default:
            "number" == typeof e && t.flexAnimate(e, !0);
        }
    };
}(jQuery);