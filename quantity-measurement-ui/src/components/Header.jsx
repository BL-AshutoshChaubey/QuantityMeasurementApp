import React, { useEffect, useState } from 'react';
import { Box, Button, Typography, Chip } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const Header = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState(null);
  const [token, setToken] = useState(null);

  useEffect(() => {
    const checkAuth = () => {
      setUsername(localStorage.getItem('USERNAME'));
      setToken(localStorage.getItem('JWT_TOKEN'));
    };
    checkAuth();
    window.addEventListener('storage', checkAuth);
    const timer = setInterval(checkAuth, 1000);
    return () => {
      window.removeEventListener('storage', checkAuth);
      clearInterval(timer);
    };
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('JWT_TOKEN');
    localStorage.removeItem('USERNAME');
    setUsername(null);
    setToken(null);
    navigate('/login');
  };

  return (
    <Box sx={{ 
      backgroundColor: '#5c6bc0', 
      color: 'white', 
      px: 3, 
      py: 2, 
      display: 'flex', 
      justifyContent: 'space-between', 
      alignItems: 'center',
      flexWrap: 'wrap',
      gap: 2
    }}>
      <Typography variant="h6" fontWeight="bold" component={Link} to="/" sx={{ color: 'white', textDecoration: 'none' }}>
        Quantity Measurement App
      </Typography>
      
      <Box sx={{ display: 'flex', alignItems: 'center', gap: 1.5 }}>
        <Button component={Link} to="/" sx={{ color: 'white', fontWeight: 'bold', textTransform: 'none' }}>
          Dashboard
        </Button>
        {token ? (
          <>
            <Chip
              icon={<AccountCircleIcon sx={{ color: 'white !important' }} />}
              label={`Hello, ${username || 'User'}`}
              sx={{ backgroundColor: 'rgba(255,255,255,0.15)', color: 'white', fontWeight: 'bold' }}
            />
            <Button onClick={handleLogout} variant="contained" sx={{ 
              backgroundColor: '#ef4444', 
              color: 'white', 
              fontWeight: 'bold', 
              textTransform: 'none',
              '&:hover': { backgroundColor: '#dc2626' }
            }}>
              Logout
            </Button>
          </>
        ) : (
          <>
            <Button component={Link} to="/login" sx={{ color: 'white', fontWeight: 'bold', textTransform: 'none' }}>
              Sign In
            </Button>
            <Button component={Link} to="/register" variant="contained" sx={{ 
              backgroundColor: 'white', 
              color: '#5c6bc0', 
              fontWeight: 'bold', 
              textTransform: 'none',
              '&:hover': { backgroundColor: '#f8fafc' }
            }}>
              Sign Up
            </Button>
          </>
        )}
      </Box>
    </Box>
  );
};

export default Header;
