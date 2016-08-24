

var Main = React.createClass({
    getInitialState: function(){
      return ({
          template: null
      });
    },
    allBooks: function(e) {

        $.get("/library/book/all", function(data) {
            var books = data.books;
            this.setState({
                template:
                    books != null ? books.map(function(item, index) {
                        return (
                            <div key={index}>
                                <h3>Book: {item.bookName}</h3>
                                <p>Book id: {item.bookId}</p>
                                <p>Author: {item.bookAuthor}</p>
                            </div>
                        );
                    }) : <p>There is no books yet</p> });
        }.bind(this));

       /* $.ajax({
            type: "GET",
            contentType : "application/json",
            url: "/library/book/all",
            success: function (data) {
                console.log("SUCCESS");
                var books = data.books;
                this.setState({
                    template:
                        books != null ? books.map(function(item, index) {
                            return (
                                <div key={index}>
                                    <p>{item.bookId}</p>
                                    <p>{item.bookName}</p>
                                    <p>{item.bookAuthor}</p>
                                </div>
                            );
                        }) : <p>There is no books yet</p> });
            },
            error: function (xhr, status, error) {
                console.log(xhr['responseText']);
            }
        }.bind(this)); */

       
    },
    createBook: function(e) {
        
        var bookId = this.refs.bookId.value;
        var bookName = this.refs.bookName.value;
        var bookAuthor = this.refs.bookAuthor.value;
        $.get("/library/book/createOrUpdate?bookId=" + bookId + "&bookName=" + bookName + 
            "&bookAuthor=" + bookAuthor, function(data) {
            this.setState({template: data.bookName != "" && data.bookAuthor != "" ?
                <p>
                    <h3>Book {data.bookName}</h3>
                    <br />
                    <p>Author {data.bookAuthor}</p>
                </p> :
                <p>
                    Cannot create book with empty parameters
                </p>
            });
        }.bind(this));
        
        /*$.ajax({
            type: "GET",
            contentType : "application/json",
            url: "/library/book/createOrUpdate",
            data: {
                bookId: bookId,
                bookName: bookName,
                bookAuthor: bookAuthor
            },
            success: function (data) {
                console.log("SUCCESS");
             
               // this.forceUpdate();
            },
            error: function (xhr, status, error) {
                console.log(xhr['responseText']);
            }
        }.bind(this)); */

       
    },
    removeBook: function(e) {
        var bookId = this.refs.bookId.value;
        $.get("/library/book/remove?bookId=" + bookId, function(data) {
            this.setState({template: data.hasOwnProperty("info") ?
                <p> {data.info} </p> :
                <p>
                    Book with id {bookId} has been removed
                </p>
            });
        }.bind(this));

    },
    render: function() {


        return (
            <div>
                <h1>Main: /main</h1>
                <br />
                Params:
                <br />  <br/>
                    <label htmlFor="bookId">Book id: </label>
                    <input ref="bookId" id="bookId" type="text" />
                <br/>  <br/>
                    <label htmlFor="bookName">Book name: </label>
                    <input id="bookName" ref="bookName" type="text" />
                <br />  <br/>
                    <label htmlFor="bookAuthor">Book author: </label>
                    <input ref="bookAuthor" type="text" />
                <br/><br/>
                Example queries:
                <br/><br/>
                    <button onClick={this.createBook}>create or update book</button>
                    <button onClick={this.removeBook}>remove book</button>
                    <button onClick={this.allBooks}>all books</button>
                <br/><br/>
                {this.state.template}
            </div>
        );
    }
});

var PasswordInput = React.createClass({
    onPasswordChange: function (event) {
        var value = this.refs.password.value;
        this.props.updatePassword(value);
    },
    render: function () {
        return (
            <p>
                <label htmlFor="password">Password: </label>
                <input ref="password" onChange={this.onPasswordChange} type="text" id="password"
                       value={this.props.data.password}  name="password" />
            </p>
        );
    }

});

var SubmitButton = React.createClass({

    handleClick: function(e) {
        var i = 1;
        this.props.onClick(e);
    },
    render: function () {
        return (
            <button id="btn"
                    onClick={this.handleClick}
                    type="submit" className="submitButton">
                Login
            </button>
        );
    }
});




var UsernameInput = React.createClass({
    onUsernameChange: function (event) {
        var value = this.refs.username.value;
        this.props.updateUsername(value);
    },
    render: function () {
        return (
            <p>
                <label htmlFor="username">Username: </label>
                <input ref="username" onChange={this.onUsernameChange} type="text" id="username"
                       value={this.props.data.username}  name="username" />
            </p>
        );
    }

});

var LoginForm = React.createClass({
    onUsernameChange: function(username) {
        var i =1;
        this.setState({username: username});
    },
    onPasswordChange: function(password) {
        this.setState({password: password});
    },
    submitClick: function(e) {
        window.localStorage.setItem('token', null);
        var element = this;
        $.ajax({
            type: "GET",
            contentType : "application/json",
            url: "/library/login",
            data: {
                "username": this.state.username,
                "password": this.state.password
            },
            success: function (data) {
                console.log("SUCCESS");

               /* expireAt = new Date;
                expireAt.setMonth(expireAt.getMonth() - 1);

                if (document.cookie != "")
                {
                    crumbs = document.cookie.split(";");
                    for(i=0; i < crumbs.length; i++)
                    {
                        crumbName = crumbs[i].split("=")[0];
                        document.cookie = crumbName + "=;expires=" + expireAt.toGMTString();
                    }
                } */
               // $.cookie('token', data.token);
                document.cookie = "token=" + data.token;
                // this.props.token = data.token;
                //window.localStorage.setItem('Token',data.token);
                //window.sessionStorage.accessToken = data.token;
                window.location.href = "#/main";

            },
            error: function (xhr, status, error) {
                console.log(xhr['responseText']);
            }
        });
    },
    getInitialState: function() {
        return ({
            username: "",
            password: "" });
    },
    render: function() {

        /* var template = data.map(function(item, index) {
         return (
         <p key={index}>
         <label htmlFor={item.label.for}>{item.label.text}</label>
         <input type={item.input.type} id={item.input.id}
         name={item.input.name} />
         </p>
         );
         }); */

        return (
            <form className="form" method="post">
                <UsernameInput updateUsername={this.onUsernameChange} data={ {"username": this.state.username} } />
                <PasswordInput updatePassword={this.onPasswordChange} data={ {"password": this.state.password} } />
                <SubmitButton onClick={this.submitClick}  />
            </form>
        );
    }

});

var App = React.createClass({

     updateToken: function(token) {
            this.setState({token: token});
     },
     getInitialState: function() {
         return ({
         route: window.location.hash.substr(1),
         //     route:  window.location.hash.substr(1),
         token: null
         });
     },
     componentDidMount: function() {
             window.addEventListener('hashchange', () => {
                 this.setState({
                     route: window.location.hash.substr(1)
                 });
         });
     },
     getToken: function() {
         var cookiesString = document.cookie;
         var token = null;

         var cookies = cookiesString.split(";");
         
         if(cookies !== 'undefined' && cookies !== undefined) {
             for (var i = 0; i < cookies.length; i++)
             {
                 var cookieKeyValuePair = cookies[i].split('=');
                 var curToken = (cookieKeyValuePair[1].trim());
                 if ("token" === (cookieKeyValuePair[0].trim()) && curToken !== null && curToken !== undefined
                        && curToken !== "null" && curToken !== "undefined") {
                     token = cookieKeyValuePair[1];
                 }
                 if (token != null) {
                    break;                
                 }
             }
         } 
        
         return token;
         
       /*  for(var i = 0; i < eqSplit.length; i++) {
             //var cookiesArr = cookies[i].split("=");
             if (eqSplit[0] == "token" && eqSplit[1] != null
                 && eqSplit[1] != "" && eqSplit[1] != 'null'
                 && eqSplit[1] != 'undefined') {
                 token = eqSplit[1];
             } else token = null;
         } */
     },
     render: function() {
    
             var Child = null;
             var token = this.getToken();
         
             switch (this.state.route) {
            
                 case '/':
                 case '/main': Child = token != null ? Main : LoginForm;  break;
                 case '/login':
                 default: Child = LoginForm; break;
             }
            // Child.props = {
          //       updateToken: this.updateToken
          //   };
             return (
             <div className="app">
                 <h1>App: /</h1>
                 <ul>
                     <li><a href='#/main'>Main</a></li>
                 </ul>
                 <Child />
             </div>
         );
     }
 });
 

ReactDOM.render(
    <App />,
    document.getElementById("root")
);

//<Router history={browserHistory}>
//<Route path='/' component={App}>
//</Route>
//</Router>

//            <IndexRoute component={LoginForm} />

// <Route path='main' component={Main} />
//<Route path='test' component={Test} />