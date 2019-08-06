import * as React from 'react';
import {IUser, pushAuthUser, removeAuthUser, submitForm, url} from '../util';
import Input from './form/Input';
import {IError, IFieldError, ILogin, IOwner, IRouterContext} from '../types';

interface ILoginProps {
    login?: ILogin;
}


interface ILoginPageState {
    login?: ILogin;
    error?: IError;
}

export default class LoginPage extends React.Component<ILoginProps, ILoginPageState> {

    context: IRouterContext;

    static contextTypes = {
        router: React.PropTypes.object.isRequired
    };


    constructor(props) {
        super(props);
        this.onInputChange = this.onInputChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            login: Object.assign({}, props.formLogin)
        };
    }

    onInputChange(name: string, value: string, fieldError: IFieldError) {
        const { login, error } = this.state;
        const modifiedLogin = Object.assign({}, login, { [name]: value });
        const newFieldErrors = error ? Object.assign({}, error.fieldErrors, {[name]: fieldError }) : {[name]: fieldError };
        this.setState({
            login: modifiedLogin,
            error: { fieldErrors: newFieldErrors }
        });
    }


    componentDidMount() {
    }

    onSubmit(event) {
        event.preventDefault();
        let user = {
            username : this.state.login.username,
            authdata : window.btoa(this.state.login.username + ':' + this.state.login.password)
        };
        console.log(user);
        pushAuthUser(user);
        submitForm('POST', 'api/auth', null, ((respStatus, response) => {
            if (respStatus !== 202) {
                removeAuthUser();
            }
        }));
    }


    render() {
        const {login, error} = this.state;

        return <span>
        <h2>Login</h2>
        <form className='form-horizontal' method='POST' action={url('/auth')}>
          <div className='form-group has-feedback'>
            <Input object={login} error={error} label='Username' name='username'
                   onChange={this.onInputChange}/>
            <Input object={login} error={error} label='Password' name='password'
                   onChange={this.onInputChange}/>
          </div>
          <div className='form-group'>
            <div className='col-sm-offset-2 col-sm-10'>
              <button className='btn btn-default' type='submit' onClick={this.onSubmit}>Sign In</button>
            </div>
          </div>
        </form>
      </span>;
    }
};
