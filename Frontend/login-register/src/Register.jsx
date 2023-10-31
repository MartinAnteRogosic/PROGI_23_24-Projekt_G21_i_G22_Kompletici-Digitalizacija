import React,{useState} from "react";
import RoleSlider from "./RoleSlider";
import PasswordCheckList from "react-password-checklist";

import img1 from './img/ante.png';
import img2 from './img/ante_zoom.png';
import img3 from './img/ante_obrnut.png';
import img4 from './img/ante_inverzni.png';


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

    const handleSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const formJSON = Object.fromEntries(formData.entries());
        console.log(formJSON);
        console.log(role);
      }

    return (
        <div className="form-container">

            <RoleSlider images={images} onChangeRole={setRole}/>

            <form className="register-form" onSubmit={handleSubmit}>
                <label htmlFor="name">Full name: </label>
                <input value={name} onChange={(e) => setName(e.target.value)} name="name" id="name" placeholder="full Name" />


                <label htmlFor="email">email</label>
                <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" placeholder="youremail@gmail.com" id="email" name="email"/>

                <label htmlFor="password">password</label>
                <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="*********" id="password" name="password"/>

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