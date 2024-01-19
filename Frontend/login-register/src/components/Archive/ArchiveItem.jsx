import React, { useState } from "react";
import './ArchiveItem.css';
import Modal from 'react-modal';
import { API } from "../../api";

const ArchiveItem = ({ id, name, doc }) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [text, setText] = useState('');
    const [deleting, setDeleting] = useState(false);
    const [password, setPass] = useState('');

    const userinfo = JSON.parse(sessionStorage.getItem("user"));

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
    };

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
        setDeleting(false);
    }

    async function handleDelete(e) {
        e.preventDefault()
        console.log(password);
        try {
            const res = await API.post("/api/v1/archive/delete-document", { documentId: id, directorPassword: password }, config);
            console.log(res);
        } catch(err) {
            console.log(err);
        }
    }

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

    return (
        <div className="archive-item">
            <button onClick={openModal} className="archive-name-button">{ name }</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <p className="scanned-text">
                        { text }
                    </p>
                    { deleting ? (
                    <>
                        <form>
                            <label>Password:</label>
                            <input value={password} onChange={(e) => setPass(e.target.value)} type="password" required/>
                            <button onClick={handleDelete}>Confirm</button> 
                        </form>
                    </>
                    ) : <button onClick={() => setDeleting(true)}>Delete from archive</button>}
                </div>
            </Modal>
        </div>
    );
}

export default ArchiveItem;