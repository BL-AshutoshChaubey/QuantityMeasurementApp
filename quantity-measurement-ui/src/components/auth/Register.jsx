import React, { useState } from 'react';
import { TextField, Button, Card, CardContent, Typography, Box, Divider, Alert } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import GoogleIcon from '@mui/icons-material/Google';
import axios from 'axios';

const Register = ({ onRegisterSuccess }) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';
    if (password !== confirmPassword) {
      setError("Passwords do not match!");
      return;
    }
    try {
      await axios.post(`${baseUrl}/auth/register`, { name, email, password });
      // Auto-login after successful registration
      const loginRes = await axios.post(`${baseUrl}/auth/login`, { email, password });
      localStorage.setItem('JWT_TOKEN', loginRes.data.token);
      localStorage.setItem('USER_NAME', loginRes.data.name);
      localStorage.setItem('USER_EMAIL', loginRes.data.email);
      if (onRegisterSuccess) onRegisterSuccess();
      navigate('/');
    } catch (err) {
      setError(err.response?.data || 'Registration failed. Please try again.');
    }
  };

  const handleGoogleRegister = () => {
    const gatewayUrl = import.meta.env.VITE_API_BASE_URL?.replace('/api/v1', '') || 'http://localhost:8080';
    window.location.href = `${gatewayUrl}/oauth2/authorization/google`;
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '80vh', py: 4 }}>
      <Card sx={{ maxWidth: 450, width: '100%', borderRadius: '16px', boxShadow: '0 12px 40px rgba(0,0,0,0.08)' }}>
        <CardContent sx={{ p: 4 }}>
          <Typography variant="h5" fontWeight="bold" textAlign="center" gutterBottom sx={{ color: '#1e293b' }}>
            Create an Account
          </Typography>
          <Typography variant="body2" color="text.secondary" textAlign="center" sx={{ mb: 3 }}>
            Join Quantity Measurement today
          </Typography>

          <form onSubmit={handleRegister}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              {error && <Alert severity="error">{error}</Alert>}
              <TextField
                label="Full Name" variant="outlined" fullWidth required
                value={name} onChange={(e) => setName(e.target.value)}
              />
              <TextField
                label="Email Address" type="email" variant="outlined" fullWidth required
                value={email} onChange={(e) => setEmail(e.target.value)}
              />
              <TextField
                label="Password" type="password" variant="outlined" fullWidth required
                value={password} onChange={(e) => setPassword(e.target.value)}
              />
              <TextField
                label="Confirm Password" type="password" variant="outlined" fullWidth required
                value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <Button
                type="submit" variant="contained" size="large" fullWidth
                sx={{ borderRadius: '8px', fontWeight: 'bold', textTransform: 'none', py: 1.2, mt: 1, backgroundColor: '#3b82f6' }}
              >
                Sign Up
              </Button>
            </Box>
          </form>

          <Box sx={{ my: 3 }}>
            <Divider>
              <Typography variant="caption" color="text.secondary">OR</Typography>
            </Divider>
          </Box>

          <Button
            variant="outlined" size="large" fullWidth
            startIcon={<GoogleIcon sx={{ color: '#ea4335' }} />}
            onClick={handleGoogleRegister}
            sx={{ borderRadius: '8px', fontWeight: 'bold', textTransform: 'none', py: 1.2, color: '#475569', borderColor: '#cbd5e1' }}
          >
            Sign up with Google
          </Button>

          <Box sx={{ mt: 3, textAlign: 'center' }}>
            <Typography variant="body2" color="text.secondary">
              Already have an account?{' '}
              <Link to="/login" style={{ color: '#3b82f6', textDecoration: 'none', fontWeight: 'bold' }}>
                Sign In
              </Link>
            </Typography>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Register;
