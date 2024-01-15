import React, { useContext, useState } from "react";
import { userContext } from "../../userContext";
import axios from "axios";
import { API } from "../../api";
import ArchiveItem from "./ArchiveItem";
import './ArchivePage.css';
import Header from "../Header/Header";

const ArchivePage = () => {
  const userinfo = JSON.parse(sessionStorage.getItem("user"));
  const [archiveData, setArchiveData] = useState({
    archiveInternalDoc: [],
    archiveOffer: [],
    archiveReceipt: [],
  });

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
      setArchiveData({
        archiveInternalDoc: data.archiveInternalDocEntities || [],
        archiveOffer: data.archiveOfferEntities || [],
        archiveReceipt: data.archiveReceiptEntities || [],
      });
    } catch (err) {
      console.log(err);
    }
  }

  return (
    <div className="archive-container">
        <Header />
      <button onClick={getArchivedDocuments}>Refresh</button>
      {archiveData.archiveInternalDoc.length > 0 ||
      archiveData.archiveOffer.length > 0 ||
      archiveData.archiveReceipt.length > 0 ? (
        <ul className="archive-items">
          {archiveData.archiveInternalDoc.map((item, index) => (
            <li key={index}>
                <ArchiveItem id={item.archIntDocID}/>
            </li>
          ))}
          {archiveData.archiveOffer.map((item, index) => (
            <li key={index}>
                <ArchiveItem id={item.arcOfferID}/>
            </li>
          ))}
          {archiveData.archiveReceipt.map((item, index) => (
            <li key={index}>
                <ArchiveItem id={item.arcRecID}/>
            </li>
          ))}
        </ul>
      ) : (
        <div>No archived data available, please refresh</div>
      )}
    </div>
  );
};

export default ArchivePage;
