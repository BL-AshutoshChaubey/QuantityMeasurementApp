import React from 'react';
import { TextField, MenuItem, Select, InputLabel, FormControl } from '@mui/material';

const ArithmeticPanel = ({
  value1, onValue1Change, unit1, onUnit1Change,
  value2, onValue2Change, unit2, onUnit2Change,
  operation, onOperationChange,
  resultValue, resultUnit, onResultUnitChange,
  units
}) => {
  const isDivide = operation === 'DIVIDE';

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', flexWrap: 'wrap' }}>
        
        {/* VALUE 1 SECTION */}
        <div style={{ flex: 1, minWidth: '200px' }}>
          <div className="section-label">Quantity 1</div>
          <div style={{ display: 'flex', gap: '0.5rem' }}>
            <TextField
              label="Value" type="number" variant="outlined" fullWidth
              value={value1} onChange={(e) => onValue1Change(e.target.value)}
              InputProps={{ style: { fontWeight: 'bold' } }}
            />
            <FormControl style={{ minWidth: 120 }}>
              <InputLabel>Unit</InputLabel>
              <Select value={unit1} label="Unit" onChange={(e) => onUnit1Change(e.target.value)}>
                {units.map((u) => <MenuItem key={u} value={u}>{u}</MenuItem>)}
              </Select>
            </FormControl>
          </div>
        </div>

        {/* OPERATION SELECTOR */}
        <div style={{ marginTop: '1.5rem', minWidth: '130px' }}>
          <FormControl fullWidth size="small">
            <InputLabel>Operation</InputLabel>
            <Select
              value={operation}
              label="Operation"
              onChange={(e) => onOperationChange(e.target.value)}
              sx={{ fontWeight: 'bold', color: '#3b82f6' }}
            >
              <MenuItem value="ADD">+ Add</MenuItem>
              <MenuItem value="SUBTRACT">- Subtract</MenuItem>
              <MenuItem value="DIVIDE">÷ Divide (Scalar)</MenuItem>
            </Select>
          </FormControl>
        </div>

        {/* VALUE 2 SECTION */}
        <div style={{ flex: 1, minWidth: '200px' }}>
          <div className="section-label">{isDivide ? "Scalar Divisor" : "Quantity 2"}</div>
          <div style={{ display: 'flex', gap: '0.5rem' }}>
            <TextField
              label="Value" type="number" variant="outlined" fullWidth
              value={value2} onChange={(e) => onValue2Change(e.target.value)}
              InputProps={{ style: { fontWeight: 'bold' } }}
            />
            {!isDivide && (
              <FormControl style={{ minWidth: 120 }}>
                <InputLabel>Unit</InputLabel>
                <Select value={unit2} label="Unit" onChange={(e) => onUnit2Change(e.target.value)}>
                  {units.map((u) => <MenuItem key={u} value={u}>{u}</MenuItem>)}
                </Select>
              </FormControl>
            )}
          </div>
        </div>

      </div>

      {/* RESULT SECTION */}
      <div>
        <div className="section-label">Result</div>
        <div style={{ display: 'flex', gap: '0.5rem', maxWidth: '300px' }}>
          <TextField
            label="Calculated Result" type="text" variant="outlined" fullWidth
            value={resultValue !== null ? resultValue : ''}
            InputProps={{ readOnly: true, style: { fontWeight: 'bold', color: '#10b981' } }}
          />
          <FormControl style={{ minWidth: 120 }}>
            <InputLabel>Unit</InputLabel>
            <Select value={resultUnit} label="Unit" onChange={(e) => onResultUnitChange(e.target.value)}>
              {units.map((u) => <MenuItem key={u} value={u}>{u}</MenuItem>)}
            </Select>
          </FormControl>
        </div>
      </div>
    </div>
  );
};

export default ArithmeticPanel;
