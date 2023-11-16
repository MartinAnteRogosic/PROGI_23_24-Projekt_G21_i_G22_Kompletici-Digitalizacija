import React,{useState} from "react";
import RoleSlider from "./RoleSlider";
import PasswordCheckList from "react-password-checklist";

import img1 from '../img/ante.png';
import img2 from '../img/ante_zoom.png';
import img3 from '../img/ante_obrnut.png';
import img4 from '../img/ante_inverzni.png';


export const Register = (props) => {

    const images = [
        {
            image: img1,
            role: 'zaposlenik'
        },
        {
            image: img2,
            role: 'revizor'
        },
        {
            image: img3,
            role: 'računovođa'
        },
        {
            image: img4,
            role: 'direktor'
        }
    ];

    const [email,  setEmail] = useState('');
    const [pass, setPass] = useState('');
    const [name, setName] = useState('');
    const [role, setRole] = useState('zaposlenik')

    async function handleSubmit(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const formJSON = Object.fromEntries(formData.entries());
        formJSON.userFunction = role
        //console.log(formJSON);
        try {
            await fetch("/api/register", {
                method: "POST",
                mode: "cors",
                headers: {"Content-Type":"application/json"},
                body: JSON.stringify(formJSON)
            });
            alert("Registracija uspješna");
        }
        catch (err) {
            alert(err);
        }
      }

    return (
        <div className="form-container">

            <RoleSlider images={images} onChangeRole={setRole}/>

            <form className="register-form" onSubmit={handleSubmit}>
                <label htmlFor="userName">Full name: </label>
                <input value={name} onChange={(e) => setName(e.target.value)} name="userName" id="userName" placeholder="full Name" />


                <label htmlFor="userEmail">email</label>
                <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="youremail@gmail.com" id="userEmail" name="userEmail"/>

                <label htmlFor="userPassword">password</label>
                <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="userPassword" name="userPassword"/>

                <PasswordCheckList rules={["minLength", "specialChar", "number", "capital"]}
                                    minLength={6}
                                    value={pass}
                                    onChange={(isValid) => {}}/>

                <button type="submit">Register</button>
            </form>
            <button onClick={ () => props.onFormSwitch('login')}>Already have an account? Login here.</button>
        </div>
    )
}