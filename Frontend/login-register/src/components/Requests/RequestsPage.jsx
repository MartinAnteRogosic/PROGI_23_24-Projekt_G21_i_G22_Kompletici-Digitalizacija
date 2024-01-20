import React, { useState, useEffect } from "react";
import { API } from "../../api";
import Header from "../Header/Header";
import './RequestsPage.css';
import ReviserRequest from "./ReviserRequest";
import AccountantRequest from "./AccountantRequest";
import DirectorRequest from "./DirectorRequest";

const RequestsPage = () => {

    useEffect(() => {
        getRequests();
    }, []);

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const [requests, setRequests] = useState([]);

    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

    const config = {
    headers: {
        Authorization: "Bearer " + userinfo.accessToken,
        "Access-Control-Allow-Origin": "*",
        },
    };

    async function getRequests() {
        try {
            //if logged-in user is revisor, send request for all documents and photos where verifierID == revisor's ID
            if (user.role === "REVISER") {
                const res = await API.get("/api/v1/document/get-revision-documents", config);
                setRequests(res.data);
                console.log(res.data);
            }
            //if logged-in user is accountant, send request for all documents and photos where verified == true, archived == false and documentType == accountant's type
            else if (user.role.includes("ACCOUNTANT")) {
                const type = user.role.split("_", 2);
                const data = {
                    type: type[1]
                };
                console.log(data);
                const res = await API.post("/api/v1/document/get-by-type", data, config);
                setRequests(res.data);
                console.log(res.data);
            }
            //if logged-in user is director, send request for all documents and photos where signatureFromID == director's ID and signed == false
            else if (user.role === "DIRECTOR") {
                const res = await API.get("/api/v1/document/documents-for-sign", config);
                setRequests(res.data);
                console.log(res.data);
            }
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div className="requests-container">
            <Header />
            <button onClick={getRequests}>Refresh</button>
            {   requests.length > 0 ? (
                <ul className="request-items">
                {requests.map((item, index) => (
                    <li key={item.documentId}>
                        { user.role === "REVISER" && (
                            <ReviserRequest id={item.documentId} name={item.documentName} photo={item.photoUrl} doc={item.documentUrl} type={item.documentType}/>
                        )}
                        { user.role.includes("ACCOUNTANT") && (
                            <AccountantRequest id={item.documentId} name={item.documentName} photo={item.photoUrl} doc={item.documentUrl} type={item.documentType}/>
                        )}
                        { user.role === "DIRECTOR" && (
                            <DirectorRequest id={item.documentId} name={item.documentName} photo={item.photoUrl} doc={item.documentUrl} type={item.documentType}/>
                        
                        )}
                    </li>
                ))}
                </ul>) : (<div className="text"> No requests to show </div>)
            }
            
        </div>
    );

};

export default RequestsPage;