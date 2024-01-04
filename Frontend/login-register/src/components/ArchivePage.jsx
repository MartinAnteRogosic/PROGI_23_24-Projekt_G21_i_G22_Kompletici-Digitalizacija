import React, { useContext } from "react";
import { userContext } from "../userContext";
import axios from "axios";
import { API } from "../api";


const ArchivePage = () => {
    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    //const { user } = useContext(userContext);
    const user = {
        "firstName": userinfo.firstName,
        "lastName": userinfo.lastName,
        "role": userinfo.role
    };
    const config = {
        headers: {
            "Authorization": "Bearer " + userinfo.accessToken,
            'Access-Control-Allow-Origin': '*'
        }
    };
    console.log(config);
    async function getArchivedDocuments() {
        try {
            const res = await API.get("/api/v1/archive/all-archive-documents", config);
            console.log(res);
          } catch (err) {
            console.log(err);
          }
    }

    return (
        <div className="archive-container">
            <button onClick={getArchivedDocuments}>Osvježi</button>
        </div>
    );


};

export default ArchivePage;