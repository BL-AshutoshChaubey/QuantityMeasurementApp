import React from 'react';
import { TextField, MenuItem, Select, InputLabel, FormControl } from '@mui/material';

const ConversionPanel = ({
  value,
  onValueChange,
  fromUnit,
  onFromUnitChange,
  toUnit,
  onToUnitChange,
  resultValue,
  units
}) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
      <div style={{ display: 'flex', gap: '2rem' }}>
        {/* FROM SECTION */}
        <div style={{ flex: 1 }}>
          <div className="section-label">From</div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <TextField
              label="Value"
              type="number"
              variant="outlined"
              fullWidth
              value={value}
              onChange={(e) => onValueChange(e.target.value)}
              InputProps={{ style: { fontWeight: 'bold', fontSize: '1.2rem' } }}
            />
            <FormControl fullWidth>
              <InputLabel>Unit</InputLabel>
              <Select
                value={fromUnit}
                label="Unit"
                onChange={(e) => onFromUnitChange(e.target.value)}
              >
                {units.map((u) => (
                  <MenuItem key={u} value={u}>{u}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </div>
        </div>

        {/* TO SECTION */}
        <div style={{ flex: 1 }}>
          <div className="section-label">To</div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <TextField
              label="Result"
              type="text"
              variant="outlined"
              fullWidth
              value={resultValue !== null ? resultValue : ''}
              InputProps={{ 
                readOnly: true,
                style: { fontWeight: 'bold', fontSize: '1.2rem', color: '#10b981' } 
              }}
            />
            <FormControl fullWidth>
              <InputLabel>Unit</InputLabel>
              <Select
                value={toUnit}
                label="Unit"
                onChange={(e) => onToUnitChange(e.target.value)}
              >
                {units.map((u) => (
                  <MenuItem key={u} value={u}>{u}</MenuItem>
                ))}
              </Select>
            </FormControl>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConversionPanel;
