(function(e){function t(t){for(var r,l,c=t[0],s=t[1],o=t[2],d=0,b=[];d<c.length;d++)l=c[d],Object.prototype.hasOwnProperty.call(u,l)&&u[l]&&b.push(u[l][0]),u[l]=0;for(r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r]);i&&i(t);while(b.length)b.shift()();return a.push.apply(a,o||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],r=!0,c=1;c<n.length;c++){var s=n[c];0!==u[s]&&(r=!1)}r&&(a.splice(t--,1),e=l(l.s=n[0]))}return e}var r={},u={app:0},a=[];function l(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,l),n.l=!0,n.exports}l.m=e,l.c=r,l.d=function(e,t,n){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(l.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)l.d(n,r,function(t){return e[t]}.bind(null,r));return n},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="/";var c=window["webpackJsonp"]=window["webpackJsonp"]||[],s=c.push.bind(c);c.push=t,c=c.slice();for(var o=0;o<c.length;o++)t(c[o]);var i=s;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"04cb":function(e,t,n){"use strict";n("813b")},"28a7":function(e,t,n){"use strict";n.r(t),n.d(t,"postRequest",(function(){return u})),n.d(t,"putRequest",(function(){return a})),n.d(t,"deleteRequest",(function(){return l}));n("d3b7"),n("4de4"),n("d81d"),n("ac1f"),n("1276"),n("498a"),n("2ca0");var r="http://localhost:8080/",u=function(e,t){fetch(r+e,{method:"post",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e.json()})).catch((function(e){return alert("Error:",e)}))},a=function(e,t){fetch(r+e,{method:"put",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"},body:JSON.stringify(t)}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},l=function(e){fetch(r+e,{method:"delete",credentials:"include",headers:{"X-XSRF-TOKEN":c(e),"Content-Type":"application/json"}}).then((function(e){return e})).catch((function(e){return alert("Error:",e)}))},c=function(e){if(fetch(r+e),!document.cookie)return"";var t=document.cookie.split(";").map((function(e){return e.trim()})).filter((function(e){return e.startsWith("XSRF-TOKEN=")}));return 0===t.length?"":decodeURIComponent(t[0].split("=")[1])}},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("7a23"),u={class:"navbar navbar-toggleable-md navbar-light bg-faded"},a={class:"collapse navbar-collapse",id:"navbarNav"},l={class:"navbar-nav"},c={class:"nav-item"},s=Object(r["f"])("User Info"),o={class:"nav-item"},i=Object(r["f"])("Get All");function d(e,t,n,d,b,p){var f=Object(r["u"])("router-link"),O=Object(r["u"])("router-view");return Object(r["p"])(),Object(r["d"])("div",null,[Object(r["e"])("nav",u,[Object(r["e"])("div",a,[Object(r["e"])("ul",l,[Object(r["e"])("li",c,[Object(r["g"])(f,{to:"/user/bab",class:"nav-link"},{default:Object(r["A"])((function(){return[s]})),_:1})]),Object(r["e"])("li",o,[Object(r["g"])(f,{to:"/getall",class:"nav-link"},{default:Object(r["A"])((function(){return[i]})),_:1})])])])]),Object(r["g"])(O,{class:"view"})])}var b={name:"App"};n("04cb");b.render=d;var p=b,f=n("6c02"),O=(n("99af"),Object(r["e"])("h1",null,"All Users",-1)),j=["onClick"],m=["onClick"],h=Object(r["e"])("br",null,null,-1),y=Object(r["e"])("br",null,null,-1);function v(e,t,n,u,a,l){var c=this;return Object(r["p"])(),Object(r["d"])("div",null,[O,(Object(r["p"])(!0),Object(r["d"])(r["a"],null,Object(r["t"])(this.users,(function(e){return Object(r["p"])(),Object(r["d"])("span",{key:e.id},[Object(r["e"])("p",null,"Username: "+Object(r["w"])(e.username),1),Object(r["e"])("p",null,"Firstname: "+Object(r["w"])(e.firstname),1),Object(r["e"])("p",null,"Lastname: "+Object(r["w"])(e.surname),1),Object(r["e"])("p",null,"Location: "+Object(r["w"])("".concat(e.location.address," ").concat(e.location.number,", ").concat(e.location.zipcode," ").concat(e.location.area,", ").concat(e.location.city)),1),Object(r["e"])("p",null,"Role: "+Object(r["w"])(e.role),1),Object(r["e"])("button",{onClick:function(t){return c.$router.push("/user/".concat(e.username))},class:"nav-link"},"User Info",8,j),Object(r["e"])("button",{onClick:function(t){c.deleteUser(e.username)}},"Delete",8,m),h,y])})),128))])}n("d3b7");var q=n("28a7"),U=q.deleteRequest,w={name:"GetAll",data:function(){return{users:[]}},created:function(){var e=this;fetch("http://localhost:8080/api/user/all",{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.users=t})).catch((function(e){return alert("Error:",e)}))},methods:{deleteUser:function(e){U("api/user/delete/".concat(e))}}};w.render=v;var g=w,C={class:"create"},S=Object(r["e"])("label",{for:"username"},"Username: ",-1),A=Object(r["e"])("br",null,null,-1),x=Object(r["e"])("label",{for:"password"},"Password: ",-1),B=Object(r["e"])("br",null,null,-1),V=Object(r["e"])("label",{for:"passwordConf"},"Password Confirmed: ",-1),k=Object(r["e"])("br",null,null,-1),N=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),R=Object(r["e"])("br",null,null,-1),E=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),P=Object(r["e"])("br",null,null,-1),T=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),z=Object(r["e"])("br",null,null,-1),Z=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),_=Object(r["e"])("br",null,null,-1),F=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),X=Object(r["e"])("br",null,null,-1),I=Object(r["e"])("label",{for:"addressCity"},"Address City: ",-1),J=Object(r["e"])("br",null,null,-1),K=Object(r["e"])("label",{for:"addressArea"},"Address Area: ",-1),L=Object(r["e"])("br",null,null,-1),M=Object(r["e"])("p",null,[Object(r["e"])("input",{type:"submit",value:"Create"})],-1);function G(e,t,n,u,a,l){return Object(r["p"])(),Object(r["d"])("div",C,[Object(r["e"])("form",{onSubmit:t[10]||(t[10]=Object(r["C"])((function(){return l.createUser&&l.createUser.apply(l,arguments)}),["prevent"])),method:"post"},[Object(r["e"])("p",null,[S,A,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.username]])]),Object(r["e"])("p",null,[x,B,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.password=t}),type:"password",name:"password",id:"password",required:"required"},null,512),[[r["y"],e.password]])]),Object(r["e"])("p",null,[V,k,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.passwordConf=t}),type:"password",name:"passwordConf",id:"passwordConf",required:"required"},null,512),[[r["y"],e.passwordConf]])]),Object(r["e"])("p",null,[N,R,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.firstname]])]),Object(r["e"])("p",null,[E,P,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.lastname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.lastname]])]),Object(r["e"])("p",null,[T,z,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.address]])]),Object(r["e"])("p",null,[Z,_,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.number]])]),Object(r["e"])("p",null,[F,X,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[7]||(t[7]=function(t){return e.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.zipcode]])]),Object(r["e"])("p",null,[I,J,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[8]||(t[8]=function(t){return e.city=t}),type:"text",name:"addressCity",id:"addressCity",required:"required"},null,512),[[r["y"],e.city]])]),Object(r["e"])("p",null,[K,L,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[9]||(t[9]=function(t){return e.area=t}),type:"text",name:"addressArea",id:"addressArea",required:"required"},null,512),[[r["y"],e.area]])]),M],32)])}n("25f0");var D=n("28a7"),$=D.postRequest,W={name:"Create",data:function(){return{username:"",password:"",passwordConf:"",firstname:"",lastname:"",city:"",address:"",number:"",zipcode:"",area:""}},methods:{createUser:function(){var e=[this.username,this.password,this.passwordConf,this.firstname,this.lastname,this.city,this.address,this.number.toString(),this.zipcode.toString(),this.area];$("api/user/create",e)}}};W.render=G;var H=W,Q=Object(r["e"])("h1",null,"User Info",-1),Y=Object(r["e"])("label",{for:"username"},"Username: ",-1),ee=Object(r["e"])("br",null,null,-1),te=Object(r["e"])("label",{for:"firstname"},"Firstname: ",-1),ne=Object(r["e"])("br",null,null,-1),re=Object(r["e"])("label",{for:"lastname"},"Lastname: ",-1),ue=Object(r["e"])("br",null,null,-1),ae=Object(r["e"])("label",{for:"addressStreet"},"Address Street: ",-1),le=Object(r["e"])("br",null,null,-1),ce=Object(r["e"])("label",{for:"addressNumber"},"Address Number: ",-1),se=Object(r["e"])("br",null,null,-1),oe=Object(r["e"])("label",{for:"addressZip"},"Address Zip: ",-1),ie=Object(r["e"])("br",null,null,-1),de=Object(r["e"])("label",{for:"role"},"Role: ",-1),be=Object(r["e"])("br",null,null,-1);function pe(e,t,n,u,a,l){return Object(r["p"])(),Object(r["d"])("div",null,[Q,Object(r["e"])("p",null,[Y,ee,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[0]||(t[0]=function(t){return e.user.username=t}),type:"text",name:"username",id:"username",required:"required"},null,512),[[r["y"],e.user.username]])]),Object(r["e"])("p",null,[te,ne,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[1]||(t[1]=function(t){return e.user.firstname=t}),type:"text",name:"firstname",id:"firstname",required:"required"},null,512),[[r["y"],e.user.firstname]])]),Object(r["e"])("p",null,[re,ue,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.user.surname=t}),type:"text",name:"lastname",id:"lastname",required:"required"},null,512),[[r["y"],e.user.surname]])]),Object(r["e"])("p",null,[ae,le,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[3]||(t[3]=function(t){return e.user.location.address=t}),type:"text",name:"addressStreet",id:"addressStreet",required:"required"},null,512),[[r["y"],e.user.location.address]])]),Object(r["e"])("p",null,[ce,se,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[4]||(t[4]=function(t){return e.user.location.number=t}),type:"number",name:"addressNumber",min:"0",id:"addressNumber",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.number]])]),Object(r["e"])("p",null,[oe,ie,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[5]||(t[5]=function(t){return e.user.location.zipcode=t}),type:"number",name:"addressZip",min:"0",id:"addressZip",required:"required",oninput:"validity.valid||(value='');"},null,512),[[r["y"],e.user.location.zipcode]])]),Object(r["e"])("p",null,[de,be,Object(r["B"])(Object(r["e"])("input",{"onUpdate:modelValue":t[6]||(t[6]=function(t){return e.user.role=t}),type:"text",name:"role",id:"role",required:"required"},null,512),[[r["y"],e.user.role]])]),Object(r["e"])("button",{onClick:t[7]||(t[7]=function(){return l.updateUser&&l.updateUser.apply(l,arguments)})},"Update"),Object(r["e"])("button",{onClick:t[8]||(t[8]=function(){return l.deleteUser&&l.deleteUser.apply(l,arguments)})},"Delete")])}var fe=n("28a7"),Oe=fe.deleteRequest,je=fe.putRequest,me={name:"UserInfo",data:function(){return{user:null}},methods:{updateUser:function(){var e=[this.user.firstname,this.user.surname,this.user.location.city,this.user.location.address,this.user.location.number,this.user.location.zipcode,this.user.location.area];je("api/user/update/".concat(this.user.username),e)},deleteUser:function(){Oe("api/user/delete/".concat(this.user.username))}},created:function(){var e=this;fetch("http://localhost:8080/api/user/get/".concat(this.$route.params.username),{credentials:"include"}).then((function(e){return e.json()})).then((function(t){return e.user=t})).catch((function(e){return alert("Error:",e)}))}};me.render=pe;var he=me,ye=[{path:"/user/:username",name:"User Info",component:he},{path:"",name:"Create",component:H},{path:"/getall",name:"Get All Users",component:g}],ve=Object(f["a"])({history:Object(f["b"])(),routes:ye}),qe=ve;Object(r["c"])(p).use(qe).mount("#app")},"813b":function(e,t,n){}});
//# sourceMappingURL=app.ef188188.js.map