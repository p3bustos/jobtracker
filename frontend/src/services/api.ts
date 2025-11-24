import axios from 'axios';
import { JobApplication, JobApplicationRequest, ApplicationStats, ApplicationStatus } from '../types/JobApplication';

const API_BASE_URL = process.env.REACT_APP_API_URL || '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const jobApplicationService = {
  getAll: async (): Promise<JobApplication[]> => {
    const response = await api.get<JobApplication[]>('/applications');
    return response.data;
  },

  getById: async (id: number): Promise<JobApplication> => {
    const response = await api.get<JobApplication>(`/applications/${id}`);
    return response.data;
  },

  create: async (data: JobApplicationRequest): Promise<JobApplication> => {
    const response = await api.post<JobApplication>('/applications', data);
    return response.data;
  },

  update: async (id: number, data: JobApplicationRequest): Promise<JobApplication> => {
    const response = await api.put<JobApplication>(`/applications/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/applications/${id}`);
  },

  getByStatus: async (status: ApplicationStatus): Promise<JobApplication[]> => {
    const response = await api.get<JobApplication[]>(`/applications/status/${status}`);
    return response.data;
  },

  getActive: async (): Promise<JobApplication[]> => {
    const response = await api.get<JobApplication[]>('/applications/active');
    return response.data;
  },

  getStats: async (): Promise<ApplicationStats> => {
    const response = await api.get<ApplicationStats>('/applications/stats');
    return response.data;
  },
};

export default api;