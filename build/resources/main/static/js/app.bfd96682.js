(function(e){function t(t){for(var r,u,o=t[0],c=t[1],s=t[2],d=0,b=[];d<o.length;d++)u=o[d],Object.prototype.hasOwnProperty.call(a,u)&&a[u]&&b.push(a[u][0]),a[u]=0;for(r in c)Object.prototype.hasOwnProperty.call(c,r)&&(e[r]=c[r]);i&&i(t);while(b.length)b.shift()();return l.push.apply(l,s||[]),n()}function n(){for(var e,t=0;t<l.length;t++){for(var n=l[t],r=!0,o=1;o<n.length;o++){var c=n[o];0!==a[c]&&(r=!1)}r&&(l.splice(t--,1),e=u(u.s=n[0]))}return e}var r={},a={app:0},l=[];function u(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.m=e,u.c=r,u.d=function(e,t,n){u.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},u.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},u.t=function(e,t){if(1&t&&(e=u(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)u.d(n,r,function(t){return e[t]}.bind(null,r));return n},u.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return u.d(t,"a",t),t},u.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},u.p="/";var o=window["webpackJsonp"]=window["webpackJsonp"]||[],c=o.push.bind(o);o.push=t,o=o.slice();for(var s=0;s<o.length;s++)t(o[s]);var i=c;l.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"28a7":function(e,t,n){"use strict";n.r(t),n.d(t,"postRequest",(function(){return a})),n.d(t,"putRequest",(function(){return l})),n.d(t,"deleteRequest",(function(){return u}));n("d3b7"),n("4de4"),n("d81d"),n("ac1f"),n("1276"),n("498a"),n("2ca0");var r="http://localhost:8080/",a=function(e,t){fetch(r+e,{method:"post",credentials:"include",headers:{"X-XSRF-TOKEN":o(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},l=function(e,t){fetch(r+e,{method:"put",credentials:"include",headers:{"X-XSRF-TOKEN":o(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},u=function(e){fetch(r+e,{method:"delete",credentials:"include",headers:{"X-XSRF-TOKEN":o(e),"Content-Type":"application/json"}}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},o=function(e){if(fetch(r+e),!document.cookie)return"";var t=document.cookie.split(";").map((function(e){return e.trim()})).filter((function(e){return e.startsWith("XSRF-TOKEN=")}));return 0===t.length?"":decodeURIComponent(t[0].split("=")[1])}},"32b9":function(e,t,n){"use strict";n("5d54")},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("7a23"),a={class:"navbar navbar-toggleable-md navbar-light bg-faded"},l={class:"collapse navbar-collapse",id:"navbarNav"},u={key:0},o={class:"navbar-nav"},c={class:"nav-item"},s=Object(r["f"])("User Info"),i={class:"nav-item"},d=Object(r["f"])("New Parkinglot"),b={class:"nav-item"},p=Object(r["f"])("Bookings"),f={class:"nav-item"},m=Object(r["f"])("Bookings On Onwed Spots"),O=Object(r["e"])("li",{class:"nav-item"},[Object(r["e"])("a",{href:"http://localhost:8080/logout",class:"nav-link"},"Logout")],-1),j={key:1},h={class:"navbar-nav"},v={class:"nav-item"},y=Object(r["f"])("New User"),g=Object(r["e"])("li",{class:"nav-item"},[Object(r["e"])("a",{href:"http://localhost:8080/login",class:"nav-link"},"Login")],-1);function U(e,t,n,U,w,k){var q=Object(r["u"])("router-link"),C=Object(r["u"])("router-view");return Object(r["p"])(),Object(r["d"])("div",null,[Object(r["e"])("nav",a,[Object(r["e"])("div",l,[e.isLoggedIn?(Object(r["p"])(),Object(r["d"])("span",u,[Object(r["e"])("ul",o,[Object(r["e"])("li",c,[Object(r["g"])(q,{to:"/user/",class:"nav-link"},{default:Object(r["A"])((function(){return[s]})),_:1})]),Object(r["e"])("li",i,[Object(r["g"])(q,{to:"/createlot",class:"nav-link"},{default:Object(r["A"])((function(){return[d]})),_:1})]),Object(r["e"])("li",b,[Object(r["g"])(q,{to:"/booking/all",class:"nav-link"},{default:Object(r["A"])((function(){return[p]})),_:1})]),Object(r["e"])("li",f,[Object(r["g"])(q,{to:"/booking/all/ownedspots",class:"nav-link"},{default:Object(r["A"])((function(){return[m]})),_:1})]),O])])):(Object(r["p"])(),Object(r["d"])("span",j,[Object(r["e"])("ul",h,[Object(r["e"])("li",v,[Object(r["g"])(q,{to:"/createuser/",class:"nav-link"},{default:Object(r["A"])((function(){return[y]})),_:1})]),g])]))])]),Object(r["g"])(C,{class:"view"})])}n("d3b7");var w={name:"App",data:function(){return{isLoggedIn:!1}},created:function(){var e=this;fetch("http://localhost:8080/api/auth/loggedin",{credentials:"include"}).then((function(e){return e.json()})).then((function(t){e.isLoggedIn=t,console.log(t)})).catch((function(e){return alert("Error:",e)}))}};n("32b9");w.render=U;var k=w,q=n("6c02"),C=(n("99af"),Object(r["e"])("h1",null,"All Users",-1)),S=["onClick"],A=["onClick"],B=Object(r["e"])("br",null,null,-1),x=Object(r["e"])("br",null,null,-1);function N(e,t,n,a,l,u){var o=this;return Object(r["p"])(),Object(r["d"])("div",null,[C,(Object(r["p"])(!0),Object(r["d"])(r["a"],null,Object(r["t"])(this.users,(function(e){return Object(r["p"])(),Object(r["d"])("span",{key:e.id},[Object(r["e"])("p",null,"Username: "+Object(r["w"])(e.username),1),Object(r["e"])("p",null,"Firstname: "+Object(r["w"])(e.firstname),1),Object(r["e"])("p",null,"Lastname: "+Object(r["w"])(e.surname),1),Object(r["e"])("p",null,"Location: "+Object(r["w"])("".concat(e.location.address," ").concat(e.location.number,", ").concat(e.location.zipcode," ").concat(e.location.area,", ").concat(e.location.city)),1),Object(r["e"])("p",null,"Role: "+Object(r["w"])(e.role),1),Object(r["e"])("button",{onClick:function(t){return o.$router.push("/user/".concat(e.username))},class:"nav-link"},"User Info",8,S),Object(r["e"])("button",{onClick:function(t){o.deleteUser(e.username)}},"Delete",8,A),B,x])})),128))])}var V=n("28a7"),R=V.deleteRequest,P={name:"GetAll",data:function(){return{users:[]}},created:function(){var e=this;fetch("http://localhost:8080/api/user/all",{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.users=t})).catch((function(e){return alert("Error:",e)}))},methods:{deleteUser:function(e){R("api/user/delete/".concat(e))}}};P.render=N;var I=P,L={class:"create"},_=Object(r["e"])("label",{for:"username"},"Username: ",-1),E=Object(r["e"])("br",null,null,-1),T=Object(r["e"])("label",{for:"password"},"Password: ",-1),z=Object(r["e"])("br",null,null,-1),Z=Object(r["e"])("label",{for:"passwordConf"},"Password Confirmed: ",-1),F=Object(r["e"])("br",null,null,-1),X=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),J=Object(r["e"])("br",null,null,-1),K=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),M=Object(r["e"])("br",null,null,-1),D=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),$=Object(r["e"])("br",null,null,-1),G=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),W=Object(r["e"])("br",null,null,-1),H=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),Q=Object(r["e"])("br",null,null,-1),Y=Object(r["e"])("label",{for:"addressCity"},"Address City: ",-1),ee=Object(r["e"])("br",null,null,-1),te=Object(r["e"])("label",{for:"addressArea"},"Address Area: ",-1),ne=Object(r["e"])("br",null,null,-1),re=Object(r["e"])("p",null,[Object(r["e"])("input",{type:"submit",value:"Create"})],-1);function ae(e,t,n,a,l,u){return Object(r["p"])(),Object(r["d"])("div",L,[Object(r["e"])("form",{onSubmit:t[10]||(t[10]=Object(r["C"])((function(){return u.createUser&&u.createUser.apply(u,arguments)}),["prevent"])),method:"post"},[Object(r["e"])("p",null,[_,E,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.username]])]),Object(r["e"])("p",null,[T,z,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.password=t}),type:"password",name:"password",id:"password",required:"required"},null,512),[[r["y"],e.password]])]),Object(r["e"])("p",null,[Z,F,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.passwordConf=t}),type:"password",name:"passwordConf",id:"passwordConf",required:"required"},null,512),[[r["y"],e.passwordConf]])]),Object(r["e"])("p",null,[X,J,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.firstname]])]),Object(r["e"])("p",null,[K,M,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.lastname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.lastname]])]),Object(r["e"])("p",null,[D,$,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.address]])]),Object(r["e"])("p",null,[G,W,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.number]])]),Object(r["e"])("p",null,[H,Q,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[7]||(t[7]=function(t){return e.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.zipcode]])]),Object(r["e"])("p",null,[Y,ee,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[8]||(t[8]=function(t){return e.city=t}),type:"text",name:"addressCity",id:"addressCity",required:"required"},null,512),[[r["y"],e.city]])]),Object(r["e"])("p",null,[te,ne,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[9]||(t[9]=function(t){return e.area=t}),type:"text",name:"addressArea",id:"addressArea",required:"required"},null,512),[[r["y"],e.area]])]),re],32)])}n("25f0");var le=n("28a7"),ue=le.postRequest,oe={name:"CreateUser",data:function(){return{username:"",password:"",passwordConf:"",firstname:"",lastname:"",city:"",address:"",number:"",zipcode:"",area:""}},methods:{createUser:function(){var e=[this.username,this.password,this.passwordConf,this.firstname,this.lastname,this.city,this.address,this.number.toString(),this.zipcode.toString(),this.area];ue("api/user/create",e)}}};oe.render=ae;var ce=oe,se=Object(r["e"])("h1",null,"User Info",-1),ie=Object(r["e"])("label",{for:"username"},"Username: ",-1),de=Object(r["e"])("br",null,null,-1),be=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),pe=Object(r["e"])("br",null,null,-1),fe=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),me=Object(r["e"])("br",null,null,-1),Oe=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),je=Object(r["e"])("br",null,null,-1),he=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),ve=Object(r["e"])("br",null,null,-1),ye=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),ge=Object(r["e"])("br",null,null,-1),Ue=Object(r["e"])("label",{for:"role"},"Role: ",-1),we=Object(r["e"])("br",null,null,-1);function ke(e,t,n,a,l,u){return Object(r["p"])(),Object(r["d"])("div",null,[se,Object(r["e"])("p",null,[ie,de,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.user.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.user.username]])]),Object(r["e"])("p",null,[be,pe,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.user.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.user.firstname]])]),Object(r["e"])("p",null,[fe,me,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.user.surname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.user.surname]])]),Object(r["e"])("p",null,[Oe,je,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.user.location.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.user.location.address]])]),Object(r["e"])("p",null,[he,ve,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.user.location.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.number]])]),Object(r["e"])("p",null,[ye,ge,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.user.location.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.zipcode]])]),Object(r["e"])("p",null,[Ue,we,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.user.role=t}),type:"text",name:"role",id:"role",required:"required"},null,512),[[r["y"],e.user.role]])]),Object(r["e"])("button",{onClick:t[7]||(t[7]=function(){return u.updateUser&&u.updateUser.apply(u,arguments)})},"Update"),Object(r["e"])("button",{onClick:t[8]||(t[8]=function(){return u.deleteUser&&u.deleteUser.apply(u,arguments)})},"Delete")])}var qe=n("28a7"),Ce=qe.deleteRequest,Se=qe.putRequest,Ae={name:"UserInfo",data:function(){return{user:null}},methods:{updateUser:function(){var e=[this.user.firstname,this.user.surname,this.user.location.city,this.user.location.address,this.user.location.number,this.user.location.zipcode,this.user.location.area];Se("api/user/update/".concat(this.user.username),e)},deleteUser:function(){Ce("api/user/delete/".concat(this.user.username))}},created:function(){var e=this;fetch("http://localhost:8080/api/user/get/".concat(this.$route.params.username),{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.user=t})).catch((function(e){return alert("Error:",e)}))}};Ae.render=ke;var Be=Ae;function xe(e,t,n,r,a,l){return null}var Ne={name:"CreateLot"};Ne.render=xe;var Ve=Ne;function Re(e,t,n,r,a,l){return null}var Pe={name:"AllLots"};Pe.render=Re;var Ie=Pe;function Le(e,t,n,r,a,l){return null}var _e={name:"LotInfo"};_e.render=Le;var Ee=_e;function Te(e,t,n,r,a,l){return null}var ze={name:"CreateSpot"};ze.render=Te;var Ze=ze;function Fe(e,t,n,r,a,l){return null}var Xe={name:"Search"};Xe.render=Fe;var Je=Xe;function Ke(e,t,n,r,a,l){return null}var Me={name:"Result"};Me.render=Ke;var De=Me;function $e(e,t,n,r,a,l){return null}var Ge={name:"AllBookings"};Ge.render=$e;var We=Ge;function He(e,t,n,r,a,l){return null}var Qe={name:"AllBookingsOnOwnSpots"};Qe.render=He;var Ye=Qe;function et(e,t,n,r,a,l){return null}var tt={name:"BookingInfo"};tt.render=et;var nt=tt,rt=[{path:"/user/:username",name:"User Info",component:Be},{path:"/user/all",name:"All Users",component:I},{path:"/createuser",name:"Create User",component:ce},{path:"/createlot",name:"Create Parkinglot",component:Ve},{path:"/lot/all",name:"All Parkinglots",component:Ie},{path:"/lot/:id",name:"Parkinglot Info",component:Ee},{path:"/lot/:id/createspot",name:"Create Parkingspot",component:Ze},{path:"/lot/:id/search",name:"Search",component:Je},{path:"/lot/:id/result",name:"Result",component:De},{path:"/booking/:id",name:"Booking Info",component:nt},{path:"/booking/all",name:"All Bookings",component:We},{path:"/booking/all/ownedspots",name:"All Bookings On Owned Spots",component:Ye}],at=Object(q["a"])({history:Object(q["b"])(),routes:rt}),lt=at;Object(r["c"])(k).use(lt).mount("#app")},"5d54":function(e,t,n){}});
//# sourceMappingURL=app.bfd96682.js.map