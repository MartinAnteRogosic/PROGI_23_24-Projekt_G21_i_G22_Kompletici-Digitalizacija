import React, { useState } from 'react';
import { API } from '../api';
import './UploadFiles.css';
import ImageDocument from './ImageDocument';

const UploadFiles = () => {

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const [selectedFiles, setSelectedFiles] = useState([]);
    const [previewImages, setPreviewImages] = useState([]);

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
    };

    const handleFileChange = (e) => {
        const files = e.target.files;
        console.log(files);

        setSelectedFiles(files);

        const previews = Array.from(files).map((file) => URL.createObjectURL(file));
        setPreviewImages(previews);
    };

    async function handleSubmit(e) {
        e.preventDefault();

        const formData = new FormData();
        //console.log(selectedFiles);
        for (const file of selectedFiles){
            formData.append('file', file);
        };
        
        for (const value of formData.values()) {
            console.log(value);
        };

        try {
            const res = await API.post('/ocr', formData, config);
            console.log(res);
        } catch(err) {
            console.log(err);
        }
  };

    return (
        <div className='upload-container'>
        <form onSubmit={handleSubmit} className='upload-form'>
            <label htmlFor="imageUpload">Select Images:</label>
            <input
            type="file"
            id="imageUpload"
            name="images"
            accept="image/*"
            multiple
            onChange={handleFileChange}
            />

            {previewImages.length > 0 && (
            <div>
                <p>Preview:</p>
                {previewImages.map((preview, index) => (
                <img key={index} src={preview} alt={`Preview ${index}`} style={{ maxWidth: '100px', maxHeight: '100px', margin: '5px' }} />
                ))}
            </div>
            )}

        <button type="submit">Submit</button>
        <ImageDocument />
        
      </form>
    </div>
  );
};

export default UploadFiles;
