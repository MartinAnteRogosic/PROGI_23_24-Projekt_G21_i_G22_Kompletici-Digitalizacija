import React, { useState } from "react";
import MyImg from './img/ante.png';

export const Login = (props) => {
  const [email, setEmail] = useState('');
  const [pass, setPass] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const formJSON = Object.fromEntries(formData.entries());
    //console.log(formJSON);
    fetch("/api/login", {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify(formJSON)
    }).then((res) => {
      console.log(res.text())
    });
  }


  return (
    <div className="form-container">

      {/* <div className="imgcontainer">
        <img src={MyImg} alt="MyImg" className="circular-image" />
        </div> */}

      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="userEmail">email</label>
        <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="youremail@gmail.com" id="userEmail" name="userEmail" />

        <label htmlFor="userPassword">password</label>
        <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="userPassword" name="userPassword" />

        <button type="submit">Log In</button>

      </form>

      <button onClick={() => props.onFormSwitch('register')}>Don't have an account? Register here.</button>
      
    </div>
  )
}
