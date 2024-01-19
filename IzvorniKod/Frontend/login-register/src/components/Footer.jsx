import React from "react";
import logo_img from '../img/logo.png';
import './Footer.css';

const Footer = () => {

    return (
        <div className="footer">
            <span>POWERED BY</span>
            <img src={logo_img} alt="logo" />
        </div>
    )
    
};

export default Footer;