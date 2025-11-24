import React, { useState, useEffect } from 'react';
import {
  Box,
  Card,
  CardContent,
  Grid,
  Typography,
  CircularProgress,
  Alert,
  Button,
  Paper,
} from '@mui/material';
import {
  TrendingUp,
  WorkOutline,
  Schedule,
  Cancel,
  CheckCircle,
  Add,
  List,
  CalendarToday,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { jobApplicationService } from '../services/api';
import { ApplicationStats } from '../types/JobApplication';

const Dashboard: React.FC = () => {
  const [stats, setStats] = useState<ApplicationStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await jobApplicationService.getStats();
      setStats(data);
    } catch (err) {
      setError('Failed to load statistics. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress size={60} />
      </Box>
    );
  }

  if (error) {
    return (
      <Box p={3}>
        <Alert severity="error" onClose={() => setError(null)}>
          {error}
        </Alert>
      </Box>
    );
  }

  const statCards = [
    {
      title: 'Total Applications',
      value: stats?.total || 0,
      icon: <TrendingUp fontSize="large" />,
      color: '#1976d2',
      bgColor: '#e3f2fd',
    },
    {
      title: 'Active',
      value: stats?.active || 0,
      icon: <WorkOutline fontSize="large" />,
      color: '#2e7d32',
      bgColor: '#e8f5e9',
    },
    {
      title: 'In Interview',
      value: stats?.inInterview || 0,
      icon: <Schedule fontSize="large" />,
      color: '#ed6c02',
      bgColor: '#fff4e5',
    },
    {
      title: 'Rejected',
      value: stats?.rejected || 0,
      icon: <Cancel fontSize="large" />,
      color: '#d32f2f',
      bgColor: '#ffebee',
    },
    {
      title: 'Accepted',
      value: stats?.accepted || 0,
      icon: <CheckCircle fontSize="large" />,
      color: '#388e3c',
      bgColor: '#e8f5e9',
    },
  ];

  return (
    <Box>
      {/* Header */}
      <Box mb={4}>
        <Typography variant="h4" fontWeight="bold" gutterBottom>
          Dashboard
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Track your job application progress and stay organized
        </Typography>
      </Box>

      {/* Stats Cards */}
      <Grid container spacing={3} mb={4}>
        {statCards.map((stat, index) => (
          <Grid key={index}>
            <Card
              sx={{
                height: '100%',
                transition: 'transform 0.2s, box-shadow 0.2s',
                '&:hover': {
                  transform: 'translateY(-4px)',
                  boxShadow: 4,
                },
              }}
            >
              <CardContent>
                <Box display="flex" justifyContent="space-between" alignItems="flex-start">
                  <Box>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      {stat.title}
                    </Typography>
                    <Typography variant="h3" fontWeight="bold">
                      {stat.value}
                    </Typography>
                  </Box>
                  <Box
                    sx={{
                      backgroundColor: stat.bgColor,
                      color: stat.color,
                      borderRadius: '50%',
                      width: 56,
                      height: 56,
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    {stat.icon}
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Quick Actions */}
      <Paper elevation={2} sx={{ p: 3 }}>
        <Typography variant="h6" fontWeight="bold" gutterBottom>
          Quick Actions
        </Typography>
        <Grid container spacing={2} mt={1}>
          <Grid>
            <Button
              fullWidth
              variant="contained"
              size="large"
              startIcon={<Add />}
              onClick={() => navigate('/applications/new')}
              sx={{ py: 2 }}
            >
              Add New Application
            </Button>
          </Grid>
          <Grid>
            <Button
              fullWidth
              variant="outlined"
              size="large"
              startIcon={<List />}
              onClick={() => navigate('/applications')}
              sx={{ py: 2 }}
            >
              View All Applications
            </Button>
          </Grid>
          <Grid>
            <Button
              fullWidth
              variant="outlined"
              size="large"
              startIcon={<CalendarToday />}
              onClick={() => navigate('/applications?filter=interview')}
              sx={{ py: 2 }}
            >
              View Interviews
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
};

export default Dashboard;