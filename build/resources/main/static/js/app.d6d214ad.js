(function(e){function t(t){for(var r,l,o=t[0],c=t[1],s=t[2],d=0,b=[];d<o.length;d++)l=o[d],Object.prototype.hasOwnProperty.call(u,l)&&u[l]&&b.push(u[l][0]),u[l]=0;for(r in c)Object.prototype.hasOwnProperty.call(c,r)&&(e[r]=c[r]);i&&i(t);while(b.length)b.shift()();return a.push.apply(a,s||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],r=!0,o=1;o<n.length;o++){var c=n[o];0!==u[c]&&(r=!1)}r&&(a.splice(t--,1),e=l(l.s=n[0]))}return e}var r={},u={app:0},a=[];function l(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,l),n.l=!0,n.exports}l.m=e,l.c=r,l.d=function(e,t,n){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(l.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)l.d(n,r,function(t){return e[t]}.bind(null,r));return n},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="/";var o=window["webpackJsonp"]=window["webpackJsonp"]||[],c=o.push.bind(o);o.push=t,o=o.slice();for(var s=0;s<o.length;s++)t(o[s]);var i=c;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"04cb":function(e,t,n){"use strict";n("813b")},"28a7":function(e,t,n){"use strict";n.r(t),n.d(t,"postRequest",(function(){return u})),n.d(t,"postRequestLogin",(function(){return a})),n.d(t,"putRequest",(function(){return l})),n.d(t,"deleteRequest",(function(){return o}));n("d3b7"),n("4de4"),n("d81d"),n("ac1f"),n("1276"),n("498a"),n("2ca0");var r="http://localhost:8080/",u=function(e,t){fetch(r+e,{method:"post",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},a=function(e){fetch("http://localhost:8080/login",{method:"post",credentials:"include",headers:{"X-XSRF-TOKEN":c("api/user/create"),"Content-Type":"application/x-www-form-urlencoded"},body:e}).then((function(e){})).catch((function(e){console.error(e)}))},l=function(e,t){fetch(r+e,{method:"put",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},o=function(e){fetch(r+e,{method:"delete",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"}}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},c=function(e){if(fetch(r+e),!document.cookie)return"";var t=document.cookie.split(";").map((function(e){return e.trim()})).filter((function(e){return e.startsWith("XSRF-TOKEN=")}));return 0===t.length?"":decodeURIComponent(t[0].split("=")[1])}},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("7a23"),u={class:"navbar navbar-toggleable-md navbar-light bg-faded"},a={class:"collapse navbar-collapse",id:"navbarNav"},l={class:"navbar-nav"},o={class:"nav-item"},c=Object(r["f"])("User Info"),s={class:"nav-item"},i=Object(r["f"])("Get All");function d(e,t,n,d,b,p){var m=Object(r["u"])("router-link"),f=Object(r["u"])("router-view");return Object(r["p"])(),Object(r["d"])("div",null,[Object(r["e"])("nav",u,[Object(r["e"])("div",a,[Object(r["e"])("ul",l,[Object(r["e"])("li",o,[Object(r["g"])(m,{to:"/user/bab",class:"nav-link"},{default:Object(r["A"])((function(){return[c]})),_:1})]),Object(r["e"])("li",s,[Object(r["g"])(m,{to:"/getall",class:"nav-link"},{default:Object(r["A"])((function(){return[i]})),_:1})])])])]),Object(r["g"])(f,{class:"view"})])}var b={name:"App"};n("04cb");b.render=d;var p=b,m=n("6c02"),f=(n("99af"),Object(r["e"])("h1",null,"All Users",-1)),O=["onClick"],j=["onClick"],h=Object(r["e"])("br",null,null,-1),y=Object(r["e"])("br",null,null,-1);function v(e,t,n,u,a,l){var o=this;return Object(r["p"])(),Object(r["d"])("div",null,[f,(Object(r["p"])(!0),Object(r["d"])(r["a"],null,Object(r["t"])(this.users,(function(e){return Object(r["p"])(),Object(r["d"])("span",{key:e.id},[Object(r["e"])("p",null,"Username: "+Object(r["w"])(e.username),1),Object(r["e"])("p",null,"Firstname: "+Object(r["w"])(e.firstname),1),Object(r["e"])("p",null,"Lastname: "+Object(r["w"])(e.surname),1),Object(r["e"])("p",null,"Location: "+Object(r["w"])("".concat(e.location.address," ").concat(e.location.number,", ").concat(e.location.zipcode," ").concat(e.location.area,", ").concat(e.location.city)),1),Object(r["e"])("p",null,"Role: "+Object(r["w"])(e.role),1),Object(r["e"])("button",{onClick:function(t){return o.$router.push("/user/".concat(e.username))},class:"nav-link"},"User Info",8,O),Object(r["e"])("button",{onClick:function(t){o.deleteUser(e.username)}},"Delete",8,j),h,y])})),128))])}n("d3b7");var w=n("28a7"),q=w.deleteRequest,U={name:"GetAll",data:function(){return{users:[]}},created:function(){var e=this;fetch("http://localhost:8080/api/user/all",{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.users=t})).catch((function(e){return alert("Error:",e)}))},methods:{deleteUser:function(e){q("api/user/delete/".concat(e))}}};U.render=v;var g=U,C={class:"create"},S=Object(r["e"])("label",{for:"username"},"Username: ",-1),x=Object(r["e"])("br",null,null,-1),A=Object(r["e"])("label",{for:"password"},"Password: ",-1),B=Object(r["e"])("br",null,null,-1),V=Object(r["e"])("label",{for:"passwordConf"},"Password Confirmed: ",-1),R=Object(r["e"])("br",null,null,-1),k=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),N=Object(r["e"])("br",null,null,-1),L=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),T=Object(r["e"])("br",null,null,-1),E=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),P=Object(r["e"])("br",null,null,-1),F=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),X=Object(r["e"])("br",null,null,-1),z=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),Z=Object(r["e"])("br",null,null,-1),_=Object(r["e"])("label",{for:"addressCity"},"Address City: ",-1),I=Object(r["e"])("br",null,null,-1),K=Object(r["e"])("label",{for:"addressArea"},"Address Area: ",-1),J=Object(r["e"])("br",null,null,-1),M=Object(r["e"])("p",null,[Object(r["e"])("input",{type:"submit",value:"Create"})],-1);function D(e,t,n,u,a,l){return Object(r["p"])(),Object(r["d"])("div",C,[Object(r["e"])("form",{onSubmit:t[10]||(t[10]=Object(r["C"])((function(){return l.createUser&&l.createUser.apply(l,arguments)}),["prevent"])),method:"post"},[Object(r["e"])("p",null,[S,x,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.username]])]),Object(r["e"])("p",null,[A,B,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.password=t}),type:"password",name:"password",id:"password",required:"required"},null,512),[[r["y"],e.password]])]),Object(r["e"])("p",null,[V,R,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.passwordConf=t}),type:"password",name:"passwordConf",id:"passwordConf",required:"required"},null,512),[[r["y"],e.passwordConf]])]),Object(r["e"])("p",null,[k,N,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.firstname]])]),Object(r["e"])("p",null,[L,T,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.lastname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.lastname]])]),Object(r["e"])("p",null,[E,P,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.address]])]),Object(r["e"])("p",null,[F,X,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.number]])]),Object(r["e"])("p",null,[z,Z,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[7]||(t[7]=function(t){return e.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.zipcode]])]),Object(r["e"])("p",null,[_,I,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[8]||(t[8]=function(t){return e.city=t}),type:"text",name:"addressCity",id:"addressCity",required:"required"},null,512),[[r["y"],e.city]])]),Object(r["e"])("p",null,[K,J,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[9]||(t[9]=function(t){return e.area=t}),type:"text",name:"addressArea",id:"addressArea",required:"required"},null,512),[[r["y"],e.area]])]),M],32)])}n("25f0");var G=n("28a7"),$=G.postRequest,W={name:"Create",data:function(){return{username:"",password:"",passwordConf:"",firstname:"",lastname:"",city:"",address:"",number:"",zipcode:"",area:""}},methods:{createUser:function(){var e=[this.username,this.password,this.passwordConf,this.firstname,this.lastname,this.city,this.address,this.number.toString(),this.zipcode.toString(),this.area];$("api/user/create",e)}}};W.render=D;var H=W,Q=Object(r["e"])("h1",null,"User Info",-1),Y=Object(r["e"])("label",{for:"username"},"Username: ",-1),ee=Object(r["e"])("br",null,null,-1),te=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),ne=Object(r["e"])("br",null,null,-1),re=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),ue=Object(r["e"])("br",null,null,-1),ae=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),le=Object(r["e"])("br",null,null,-1),oe=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),ce=Object(r["e"])("br",null,null,-1),se=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),ie=Object(r["e"])("br",null,null,-1),de=Object(r["e"])("label",{for:"role"},"Role: ",-1),be=Object(r["e"])("br",null,null,-1);function pe(e,t,n,u,a,l){return Object(r["p"])(),Object(r["d"])("div",null,[Q,Object(r["e"])("p",null,[Y,ee,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.user.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.user.username]])]),Object(r["e"])("p",null,[te,ne,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.user.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.user.firstname]])]),Object(r["e"])("p",null,[re,ue,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.user.surname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.user.surname]])]),Object(r["e"])("p",null,[ae,le,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.user.location.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.user.location.address]])]),Object(r["e"])("p",null,[oe,ce,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.user.location.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.number]])]),Object(r["e"])("p",null,[se,ie,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.user.location.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.zipcode]])]),Object(r["e"])("p",null,[de,be,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.user.role=t}),type:"text",name:"role",id:"role",required:"required"},null,512),[[r["y"],e.user.role]])]),Object(r["e"])("button",{onClick:t[7]||(t[7]=function(){return l.updateUser&&l.updateUser.apply(l,arguments)})},"Update"),Object(r["e"])("button",{onClick:t[8]||(t[8]=function(){return l.deleteUser&&l.deleteUser.apply(l,arguments)})},"Delete")])}var me=n("28a7"),fe=me.deleteRequest,Oe=me.putRequest,je={name:"UserInfo",data:function(){return{user:null}},methods:{updateUser:function(){var e=[this.user.firstname,this.user.surname,this.user.location.city,this.user.location.address,this.user.location.number,this.user.location.zipcode,this.user.location.area];Oe("api/user/update/".concat(this.user.username),e)},deleteUser:function(){fe("api/user/delete/".concat(this.user.username))}},created:function(){var e=this;fetch("http://localhost:8080/api/user/get/".concat(this.$route.params.username),{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.user=t})).catch((function(e){return alert("Error:",e)}))}};je.render=pe;var he=je,ye=Object(r["e"])("label",{for:"username"},"Username: ",-1),ve=Object(r["e"])("br",null,null,-1),we=Object(r["e"])("label",{for:"password"},"Password: ",-1),qe=Object(r["e"])("br",null,null,-1),Ue=Object(r["e"])("p",null,[Object(r["e"])("input",{type:"submit",value:"Login"})],-1);function ge(e,t,n,u,a,l){return Object(r["p"])(),Object(r["d"])("div",null,[Object(r["e"])("form",{onSubmit:t[2]||(t[2]=Object(r["C"])((function(){return l.performLogin&&l.performLogin.apply(l,arguments)}),["prevent"])),method:"post"},[Object(r["e"])("p",null,[ye,ve,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.username]])]),Object(r["e"])("p",null,[we,qe,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.password=t}),type:"password",name:"password",id:"password",required:"required"},null,512),[[r["y"],e.password]])]),Ue],32)])}n("498a");var Ce=n("28a7"),Se=Ce.postRequestLogin,xe={name:"Login",data:function(){return{username:"",password:""}},methods:{performLogin:function(){var e=new FormData;e.set("username",this.username.trim()),e.set("password",this.password.trim()),Se(e)}}};xe.render=ge;var Ae=xe,Be=[{path:"/user/:username",name:"User Info",component:he},{path:"/createuser",name:"Create",component:H},{path:"/getall",name:"Get All Users",component:g},{path:"/login",name:"Login",component:Ae}],Ve=Object(m["a"])({history:Object(m["b"])(),routes:Be}),Re=Ve;Object(r["c"])(p).use(Re).mount("#app")},"813b":function(e,t,n){}});
//# sourceMappingURL=app.d6d214ad.js.map