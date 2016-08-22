/*
 <form  method="post">

 <p>
 <label for="username">Username</label>
 <input type="text" id="username" name="username"/>
 </p>
 <p>
 <label for="password">Password</label>
 <input type="password" id="password" name="password"/>
 </p>
 <!--  <input type="hidden"
 name="${_csrf.parameterName}"
 value="${_csrf.token}"/> -->
 <button id="submit_btn" type="submit" class="btn">Log in</button>
 </form>
 */

var loginFormProps = [{ label: { for: "username", text: "Username"},
                        input: { type: "text", id: "username", name: "username" }},
    {
        label: { for: "password", text: "Password"},
        input: { type: "text", id: "password", name: "password" }
        
    }];

var SubmitBtn = React.createClass({
    
    handleClick: function(e) {
        
        this.props.onClick();
        /*var items = this.state.items.filter(function(item, i) {
            return index !== i;
        });
        this.setState({items: items}, function() {
            if (items.length === 1) {
                this.refs.item0.animate();
            }
        }.bind(this)); */
        //alert("can debug!");
        
   
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
    submitClick: function() {
        window.localStorage.setItem('token', null);
        alert(this.state);

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
                document.cookie = "token=" + data.token;
                //window.localStorage.setItem('Token',data.token);
                //window.sessionStorage.accessToken = data.token;
                window.location.href = "/library/main";

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
            <SubmitBtn onClick={this.submitClick}  />
        </form>
      );
    }

});


var App = ReactDOM.render(

  <div className="app">
    Hi im root;
      <LoginForm />
  </div>,
  document.getElementById("root")

);