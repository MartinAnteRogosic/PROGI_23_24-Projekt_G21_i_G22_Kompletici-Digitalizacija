import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { userContext } from "../userContext";
import { useContext } from "react";
import { API } from "../api";
import "./Login.css";

export const Login = (props) => {
  const [email, setEmail] = useState('');
  const [password, setPass] = useState('');
  const navigate = useNavigate();
  const { user, setUser } = useContext(userContext);

  async function handleSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const formJSON = Object.fromEntries(formData.entries());
    console.log(formJSON);
    try {
      const res = await API.post("/api/v1/authenticate/login", formJSON);
      console.log(res);
      if (res.data.accessToken) {
        sessionStorage.setItem("user", JSON.stringify(res.data));
        const user = {
          "firstName": res.data.firstName,
          "lastName": res.data.lastName,
          "role": res.data.role
        };
        setUser(user);
        navigate('/home');
      }
    } catch (err) {
      alert(err);
    }
  }

  return (
    <div className="form-container">

      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="email">email</label>
        <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="youremail@gmail.com" id="email" name="email" required/>

        <label htmlFor="password">password</label>
        <input value={password} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="password" name="password" required/>

        <button type="submit">Log In</button>

      </form>

      <button onClick={() => props.onFormSwitch('register')}>Don't have an account? Register here.</button>
      
    </div>
  )
}
