import React from "react";
import './RequestItem.css';

const RequestItem = () => {
    const userinfo = JSON.parse(sessionStorage.getItem("user"));

    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

    return (
        <div className="request-item">
            <span>item thingy</span>
            {user.role == 'REVISOR' ?
            <div><button>Verify</button>
            <button>Send to accountant</button></div> : null}
            {user.role == 'ACCOUNTANT' ?
            <div><button>Archive document</button>
            <button>Get signature</button></div> : null}
            {user.role == 'DIRECTOR' ?
            <div><button>Share</button>
            <button>Sign</button></div> : null}
        </div>
    );

};

export default RequestItem;