import React, { useEffect, useState } from 'react';
import { Card, CardContent, Typography, Box, CircularProgress, IconButton, Chip } from '@mui/material';
import RefreshIcon from '@mui/icons-material/Refresh';
import HistoryToggleOffIcon from '@mui/icons-material/HistoryToggleOff';
import { fetchHistory } from '../services/api';

const HistoryPanel = ({ refreshTrigger }) => {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const loadHistory = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchHistory();
      setHistory(data);
    } catch (err) {
      setError('Unable to fetch history.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadHistory();
  }, [refreshTrigger]);

  return (
    <Card sx={{ mt: 4, width: '100%', maxWidth: '800px', borderRadius: '16px', boxShadow: '0 4px 20px rgba(0,0,0,0.05)', backgroundColor: '#ffffff' }}>
      <CardContent sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
            <HistoryToggleOffIcon sx={{ color: '#64748b' }} />
            <Typography variant="h6" fontWeight="bold" sx={{ color: '#1e293b' }}>
              Action Audit Trail
            </Typography>
          </Box>
          <IconButton onClick={loadHistory} size="small" sx={{ color: '#3b82f6' }} title="Refresh History">
            <RefreshIcon />
          </IconButton>
        </Box>

        {loading ? (
          <Box sx={{ display: 'flex', justifyContent: 'center', my: 3 }}>
            <CircularProgress size={28} />
          </Box>
        ) : error ? (
          <Typography variant="body2" color="error" textAlign="center" sx={{ my: 2 }}>
            {error}
          </Typography>
        ) : history.length === 0 ? (
          <Typography variant="body2" color="text.secondary" textAlign="center" sx={{ my: 3 }}>
            No recent measurements recorded yet.
          </Typography>
        ) : (
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1.5 }}>
            {history.map((item) => (
              <Box
                key={item.id}
                sx={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  p: 1.5,
                  borderRadius: '8px',
                  backgroundColor: '#f8fafc',
                  border: '1px solid #f1f5f9',
                  flexWrap: 'wrap',
                  gap: 1
                }}
              >
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1.5 }}>
                  <Chip
                    label={item.actionType}
                    size="small"
                    sx={{
                      fontWeight: 'bold',
                      fontSize: '0.7rem',
                      backgroundColor: item.actionType === 'CONVERSION' ? '#e0e7ff' : '#dcfce7',
                      color: item.actionType === 'CONVERSION' ? '#4f46e5' : '#15803d'
                    }}
                  />
                  <Typography variant="body2" sx={{ color: '#334155', fontWeight: 500 }}>
                    {item.detail}
                  </Typography>
                </Box>
                <Typography variant="caption" sx={{ color: '#94a3b8' }}>
                  {new Date(item.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })}
                </Typography>
              </Box>
            ))}
          </Box>
        )}
      </CardContent>
    </Card>
  );
};

export default HistoryPanel;
