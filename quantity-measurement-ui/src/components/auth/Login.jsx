import React, { useState } from 'react';
import { TextField, Button, Card, CardContent, Typography, Box, Divider } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import GoogleIcon from '@mui/icons-material/Google';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    // Static simulation: redirect directly to dashboard
    navigate('/');
  };

  const handleGoogleLogin = () => {
    // Redirect to native Spring Security OAuth2 Google authorization endpoint
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '80vh' }}>
      <Card sx={{ maxWidth: 450, width: '100%', borderRadius: '16px', boxShadow: '0 12px 40px rgba(0,0,0,0.08)' }}>
        <CardContent sx={{ p: 4 }}>
          <Typography variant="h5" fontWeight="bold" textAlign="center" gutterBottom sx={{ color: '#1e293b' }}>
            Welcome Back
          </Typography>
          <Typography variant="body2" color="text.secondary" textAlign="center" sx={{ mb: 3 }}>
            Sign in to continue to Quantity Measurement
          </Typography>

          <form onSubmit={handleLogin}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2.5 }}>
              <TextField
                label="Email Address"
                type="email"
                variant="outlined"
                fullWidth
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <TextField
                label="Password"
                type="password"
                variant="outlined"
                fullWidth
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <Button
                type="submit"
                variant="contained"
                size="large"
                fullWidth
                sx={{ borderRadius: '8px', fontWeight: 'bold', textTransform: 'none', py: 1.2, backgroundColor: '#3b82f6' }}
              >
                Sign In
              </Button>
            </Box>
          </form>

          <Box sx={{ my: 3 }}>
            <Divider>
              <Typography variant="caption" color="text.secondary">OR</Typography>
            </Divider>
          </Box>

          <Button
            variant="outlined"
            size="large"
            fullWidth
            startIcon={<GoogleIcon sx={{ color: '#ea4335' }} />}
            onClick={handleGoogleLogin}
            sx={{ borderRadius: '8px', fontWeight: 'bold', textTransform: 'none', py: 1.2, color: '#475569', borderColor: '#cbd5e1' }}
          >
            Sign in with Google
          </Button>

          <Box sx={{ mt: 3, textAlign: 'center' }}>
            <Typography variant="body2" color="text.secondary">
              Don't have an account?{' '}
              <Link to="/register" style={{ color: '#3b82f6', textDecoration: 'none', fontWeight: 'bold' }}>
                Sign Up
              </Link>
            </Typography>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Login;
