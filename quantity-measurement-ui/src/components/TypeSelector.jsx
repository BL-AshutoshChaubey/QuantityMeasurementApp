import React from 'react';
import StraightenIcon from '@mui/icons-material/Straighten';
import ScaleIcon from '@mui/icons-material/Scale';
import DeviceThermostatIcon from '@mui/icons-material/DeviceThermostat';
import ScienceIcon from '@mui/icons-material/Science';
import './TypeSelector.scss';

const types = [
  { id: 'LENGTH', label: 'Length', icon: <StraightenIcon fontSize="large" color="inherit" /> },
  { id: 'WEIGHT', label: 'Weight', icon: <ScaleIcon fontSize="large" color="inherit" /> },
  { id: 'TEMPERATURE', label: 'Temperature', icon: <DeviceThermostatIcon fontSize="large" color="inherit" /> },
  { id: 'VOLUME', label: 'Volume', icon: <ScienceIcon fontSize="large" color="inherit" /> }
];

const TypeSelector = ({ selectedType, onSelect }) => {
  return (
    <div className="type-selector-container">
      <div className="section-label">Choose Type</div>
      <div className="type-grid">
        {types.map((type) => (
          <div 
            key={type.id}
            className={`type-card ${selectedType === type.id ? 'active' : ''}`}
            onClick={() => onSelect(type.id)}
          >
            <div className="icon-wrapper">
              {type.icon}
            </div>
            <span className="type-label">{type.label}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TypeSelector;
