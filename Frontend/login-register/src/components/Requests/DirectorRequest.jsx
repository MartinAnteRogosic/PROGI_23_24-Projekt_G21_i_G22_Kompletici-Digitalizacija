import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../../api";
import Modal from 'react-modal';


const DirectorRequest = ({ id, name, photo, doc }) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [signed, setSigned] = useState(false);
    const [caption, setCaption] = useState('');
    const [text, setText] = useState('');

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

    const customStyles = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: "-50%",
            transform: 'translate(-50%, -50%)',
        },
    }

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
    }

    async function openModal() { 
        setModalOpen(true);
        try {
            const res = await fetch(doc);
            const text = await res.text();
            console.log(text);
            setText(text);
        } catch(err) {
            console.log(err);
        }
        
        console.log(doc);
    }

    function closeModal() { 
        setModalOpen(false);
    }

    async function handleSigning() {
        try {
            //send request to backend to update signed to true
            const res = await API.post("/api/v1/document/sign-document", { id: id, signed: true }, config);
            console.log(res);
            setSigned(true);
        } catch (err) {
          console.log(err);
        }
    }

    async function handleShare() {
        try {
            const res = await API.post("/api/v1/document/shareOnFacebook", { documentId: id, confirm: true }, config);
            console.log(res);
        } catch (err) {
          console.log(err);
        }
    }

    return (
        <div className="request-item">
            <span>{ name }</span>
            <button onClick={openModal}>Open</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <p className="scanned-text">
                        { text }
                    </p>
                    {
                        signed ? (
                            <div>Document signed</div>
                        ) : (
                            <button onClick={handleSigning}>Sign document</button>
                        )
                    
                    }
                    <form action="">
                        <label htmlFor="caption">Caption post</label>
                        <input value={caption} onChange={(e) => setCaption(e.target.value)} type="text" id="caption" name="caption" required/>
                        <button type="submit">Share on Facebook</button>
                    </form>
                    
                </div>
            </Modal>
        </div>
    );

};

export default DirectorRequest;