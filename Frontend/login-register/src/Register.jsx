import React,{useState} from "react"

import MyImg from './img/ante.png';


export const Register = (props) => {
    const [email,  setEmail] = useState('');
    const [pass, setPass] = useState('');
    const [name, setName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(email);
    }

    return (
        <div className="form-container">

            <div className="imgcontainer">
                <img src={MyImg} alt="MyImg" className="circular-image" /> {/* Apply the CSS class */}
            </div>
            <form className="register-form" onSubmit={handleSubmit}>
                <label htmlFor="name">Full name: </label>
                <input value={name} onChange={(e) => setName(e.target.value)} name="name" id="name" placeholder="full Name" />


                <label htmlFor="email">email</label>
                <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="youremail@gmail.com" id="email" name="email"/>

                <label htmlFor="password">password</label>
                <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="password" name="password"/>

                <button type="submit">Register</button>
            </form>
            <button onClick={ () => props.onFormSwitch('login')}>Already have an account? Login here.</button>
        </div>
    )
}