import React, { useState } from "react";
import './RoleSlider.css';

const RoleSlider = ({ images, onChangeRole }) => {
    
    const [current, setCurrent] = useState(0);
    const length = images.length;

    const nextSlide = () => {
        const temp = current === length - 1 ? 0 : current + 1
        setCurrent(temp);
        onChangeRole(images[temp].role);
    };

    const prevSlide = () => {
        const temp = current === 0 ? length - 1 : current - 1
        setCurrent(temp);
        onChangeRole(images[temp].role);
    };

    if (!Array.isArray(images) || images.length <= 0) {
        return null;    
      }

    return(
        <div className="role-slider">
            <button onClick={prevSlide}>&lt;&lt;</button>
            {images.map((imgObj, index) => {
                return (
                    <div
                      className={index === current ? 'slide active' : 'slide'}
                      key={index}
                    >
                      {index === current && (
                        <><img src={imgObj.image} alt='role image' className='role-img' /><p className="role-name">{imgObj.name}</p></>       
                      )}
                    </div>
                  );
            })}
            <button onClick={nextSlide}>&gt;&gt;</button>
        </div>
    );
};

export default RoleSlider;