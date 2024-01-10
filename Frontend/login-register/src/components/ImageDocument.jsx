import React, { useState } from "react";
import Modal from 'react-modal';
import './ImageDocument.css';

const ImageDocument = (props) => {

    const [modalOpen, setModalOpen] = useState(false);

    const customStyles = {
        content: {
          top: '50%',
          left: '50%',
          right: 'auto',
          bottom: 'auto',
          marginRight: '-50%',
          transform: 'translate(-50%, -50%)',
        },
      };

    function openModal() {
        setModalOpen(true);
    };
    
    function closeModal() {
        setModalOpen(false);
    };

    function handleCorrectClick() {

    };

    function handleIncorrectClick() {

    };

    return (
        <div>
            <img src="https://firebasestorage.googleapis.com/v0/b/kompletici.appspot.com/o/e97d0dbf-c12a-4764-8d53-a5d61662c782.jpg?alt=media" alt="img"
                onClick={openModal} className="document-photo"/>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <img src="https://firebasestorage.googleapis.com/v0/b/kompletici.appspot.com/o/e97d0dbf-c12a-4764-8d53-a5d61662c782.jpg?alt=media" alt="img"
                        className="modal-document-photo"/>
                    <div>
                        <button onClick={handleCorrectClick}>Correct</button>
                        <button onClick={handleIncorrectClick}>Incorrect</button>
                    </div>
                </div>
            </Modal>
        </div>
    );
};

export default ImageDocument;