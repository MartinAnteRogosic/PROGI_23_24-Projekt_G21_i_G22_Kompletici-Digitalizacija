import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { userContext } from "../userContext";
import { useContext } from "react";

export const Login = (props) => {
  const [email, setEmail] = useState('');
  const [pass, setPass] = useState('');
  const navigate = useNavigate();
  const { user, setUser } = useContext(userContext);

  async function handleSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const formJSON = Object.fromEntries(formData.entries());
    //console.log(formJSON);
    try {
      const res = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(formJSON)
      })
      if (res.status == 200){
        const user = await res.json();
        setUser(user);
        navigate('/home');
        console.log(user);
      }
      else if (res.status == 404){
        alert("E-mail ne postoji, molimo registrirajte se");
      }
      else {
        alert("Kriva lozinka");
      }
    } catch(err) {
      alert(err);
    }
  }


  return (
    <div className="form-container">

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
