import React from 'react';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';

const ActionSelector = ({ selectedType, selectedAction, onActionChange }) => {
  const handleChange = (event, newAction) => {
    if (newAction !== null) {
      onActionChange(newAction);
    }
  };

  return (
    <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
      <div className="section-label">Choose Action</div>
      <ToggleButtonGroup
        color="primary"
        value={selectedAction}
        exclusive
        onChange={handleChange}
        aria-label="action selector"
        sx={{
          backgroundColor: '#f8fafc',
          borderRadius: '8px',
          padding: '4px',
          '& .MuiToggleButton-root': {
            border: 'none',
            borderRadius: '6px !important',
            padding: '8px 24px',
            textTransform: 'none',
            fontWeight: 600,
            color: '#64748b',
            '&.Mui-selected': {
              backgroundColor: '#3b82f6',
              color: '#ffffff',
              boxShadow: '0 2px 4px rgba(59, 130, 246, 0.3)',
              '&:hover': {
                backgroundColor: '#2563eb',
              }
            },
            '&.Mui-disabled': {
              opacity: 0.5
            }
          }
        }}
      >
        <ToggleButton value="CONVERSION" aria-label="conversion">
          Conversion
        </ToggleButton>
        <ToggleButton 
          value="ARITHMETIC" 
          aria-label="arithmetic"
          disabled={selectedType === 'TEMPERATURE'}
          title={selectedType === 'TEMPERATURE' ? "Arithmetic is disabled for Temperature" : ""}
        >
          Arithmetic
        </ToggleButton>
      </ToggleButtonGroup>
    </div>
  );
};

export default ActionSelector;
