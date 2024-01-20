import React, { useContext, useState, useEffect } from "react";
import { userContext } from "../../userContext";
import axios from "axios";
import { API } from "../../api";
import ArchiveItem from "./ArchiveItem";
import './ArchivePage.css';
import Header from "../Header/Header";

const ArchivePage = () => {

  useEffect(() => {
    getArchivedDocuments();
  }, []);

  const userinfo = JSON.parse(sessionStorage.getItem("user"));
  const [archiveData, setArchiveData] = useState([]);

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

  async function getArchivedDocuments() {
    try {
      const res = await API.get("/api/v1/archive/all-archive-documents", config);
      const data = res.data;
      console.log(data);
      setArchiveData(data);
    } catch (err) {
      alert("You do not have permission to view this page");
      console.log(err);
    }
  }

  return (
    <div className="archive-container">
        <Header />
      <button onClick={getArchivedDocuments}>Refresh</button>
      {archiveData.length > 0 ? (
        <ul className="archive-items">
          {archiveData.map((item, index) => (
            <li key={item.documentId}>
                <ArchiveItem id={item.documentId} name={item.documentName} doc={item.documentUrl}/>
            </li>
          ))}
        </ul>
      ) : (
        <div className="text-reason">No archived data available, please refresh</div>
      )}
    </div>
  );
};

export default ArchivePage;
