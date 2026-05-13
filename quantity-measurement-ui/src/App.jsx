import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import TypeSelector from './components/TypeSelector';
import ActionSelector from './components/ActionSelector';
import ConversionPanel from './components/ConversionPanel';
import ArithmeticPanel from './components/ArithmeticPanel';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import HistoryPanel from './components/HistoryPanel';
import { performConversion, performArithmetic } from './services/api';
import './styles/main.scss';

const UNIT_DICTIONARY = {
  LENGTH: ['FEET', 'INCHES', 'YARD', 'MILE'],
  WEIGHT: ['GRAM', 'KILOGRAM', 'TONNE'],
  TEMPERATURE: ['CELSIUS', 'FAHRENHEIT'],
  VOLUME: ['LITRE', 'MILLILITRE', 'GALLON']
};

const MeasurementDashboard = () => {
  const [refreshHistory, setRefreshHistory] = useState(0);
  const [selectedType, setSelectedType] = useState('LENGTH');
  const [selectedAction, setSelectedAction] = useState('CONVERSION');

  // Conversion State
  const [convValue, setConvValue] = useState(1);
  const [convFromUnit, setConvFromUnit] = useState('FEET');
  const [convToUnit, setConvToUnit] = useState('INCHES');
  const [convResult, setConvResult] = useState(null);

  // Arithmetic State
  const [arithValue1, setArithValue1] = useState(1);
  const [arithUnit1, setArithUnit1] = useState('FEET');
  const [arithValue2, setArithValue2] = useState(1);
  const [arithUnit2, setArithUnit2] = useState('INCHES');
  const [arithOperation, setArithOperation] = useState('ADD');
  const [arithResultUnit, setArithResultUnit] = useState('FEET');
  const [arithResult, setArithResult] = useState(null);

  // Handle Type Change
  useEffect(() => {
    const units = UNIT_DICTIONARY[selectedType];
    setConvFromUnit(units[0]);
    setConvToUnit(units[1] || units[0]);
    setArithUnit1(units[0]);
    setArithUnit2(units[1] || units[0]);
    setArithResultUnit(units[0]);
    setConvResult(null);
    setArithResult(null);

    if (selectedType === 'TEMPERATURE') {
      setSelectedAction('CONVERSION');
    }
  }, [selectedType]);

  // Execute Conversion
  useEffect(() => {
    if (selectedAction !== 'CONVERSION') return;
    if (convValue === '' || isNaN(convValue)) {
      setConvResult(null);
      return;
    }

    const payload = {
      value: parseFloat(convValue),
      fromUnit: convFromUnit,
      toUnit: convToUnit
    };

    performConversion(payload)
      .then(data => {
        setConvResult(data.resultValue || data.value);
        setRefreshHistory(prev => prev + 1);
      })
      .catch(err => {
        console.error(err);
        setConvResult('Error');
      });
  }, [convValue, convFromUnit, convToUnit, selectedAction]);

  // Execute Arithmetic via Backend API
  useEffect(() => {
    if (selectedAction !== 'ARITHMETIC') return;
    if (arithValue1 === '' || arithValue2 === '' || isNaN(arithValue1) || isNaN(arithValue2)) {
      setArithResult(null);
      return;
    }

    const isDivide = arithOperation === 'DIVIDE';
    const payload = {
      value1: parseFloat(arithValue1),
      unit1: arithUnit1,
      value2: parseFloat(arithValue2),
      unit2: isDivide ? "" : arithUnit2,
      operation: arithOperation,
      resultUnit: arithResultUnit
    };

    performArithmetic(payload)
      .then(data => {
        setArithResult(data.resultValue);
        setRefreshHistory(prev => prev + 1);
      })
      .catch(err => {
        console.error(err);
        setArithResult('Error');
      });
  }, [arithValue1, arithUnit1, arithValue2, arithUnit2, arithOperation, arithResultUnit, selectedAction]);

  const currentUnits = UNIT_DICTIONARY[selectedType];

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <div className="content-area">
          <TypeSelector selectedType={selectedType} onSelect={setSelectedType} />
          <ActionSelector selectedType={selectedType} selectedAction={selectedAction} onActionChange={setSelectedAction} />

          {selectedAction === 'CONVERSION' ? (
            <ConversionPanel 
              value={convValue} onValueChange={setConvValue}
              fromUnit={convFromUnit} onFromUnitChange={setConvFromUnit}
              toUnit={convToUnit} onToUnitChange={setConvToUnit}
              resultValue={convResult}
              units={currentUnits}
            />
          ) : (
            <ArithmeticPanel 
              value1={arithValue1} onValue1Change={setArithValue1}
              unit1={arithUnit1} onUnit1Change={setArithUnit1}
              value2={arithValue2} onValue2Change={setArithValue2}
              unit2={arithUnit2} onUnit2Change={setArithUnit2}
              operation={arithOperation} onOperationChange={setArithOperation}
              resultValue={arithResult}
              resultUnit={arithResultUnit} onResultUnitChange={setArithResultUnit}
              units={currentUnits}
            />
          )}
        </div>
      </div>
      {localStorage.getItem('JWT_TOKEN') && (
        <HistoryPanel refreshTrigger={refreshHistory} />
      )}
    </div>
  );
};

function App() {
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get('token');
    const username = params.get('username');
    if (token) {
      localStorage.setItem('JWT_TOKEN', token);
      if (username) localStorage.setItem('USERNAME', username);
      window.history.replaceState({}, document.title, window.location.pathname);
      window.dispatchEvent(new Event('storage'));
    }
  }, []);

  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<MeasurementDashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
