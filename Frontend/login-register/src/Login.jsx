import React, { useState } from "react";
import MyImg from './img/ante.png';

export const Login = (props) => {
  const [email, setEmail] = useState('');
  const [pass, setPass] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(email);
  }

  return (
    <div className="form-container">

    <div className="imgcontainer">
        <img src={MyImg} alt="MyImg" className="circular-image" /> {/* Apply the CSS class */}
      </div>

      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="email">email</label>
        <input value={email} type="email" placeholder="youremail@gmail.com" id="email" name="email" />

        <label htmlFor="password">password</label>
        <input value={pass} type="password" placeholder="*********" id="password" name="password" />

        <button type="submit">Log In</button>

      </form>

      <button onClick={() => props.onFormSwitch('register')}>Don't have an account? Register here.</button>
      
    </div>
  )
}
