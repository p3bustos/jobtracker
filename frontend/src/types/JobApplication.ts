export enum ApplicationStatus {
  RESEARCHING = 'RESEARCHING',
  APPLIED = 'APPLIED',
  PHONE_SCREEN = 'PHONE_SCREEN',
  TECHNICAL_INTERVIEW = 'TECHNICAL_INTERVIEW',
  ONSITE_INTERVIEW = 'ONSITE_INTERVIEW',
  OFFER = 'OFFER',
  REJECTED = 'REJECTED',
  WITHDRAWN = 'WITHDRAWN',
  ACCEPTED = 'ACCEPTED',
}

export interface JobApplication {
  id: number;
  companyName: string;
  jobTitle: string;
  status: ApplicationStatus;
  description?: string;
  notes?: string;
  location?: string;
  jobUrl?: string;
  salaryMin?: number;
  salaryMax?: number;
  appliedDate?: string;
  createdAt: string;
  updatedAt: string;
  active: boolean;
  inInterviewProcess: boolean;
}

export interface JobApplicationRequest {
  companyName: string;
  jobTitle: string;
  status: ApplicationStatus;
  description?: string;
  notes?: string;
  location?: string;
  jobUrl?: string;
  salaryMin?: number;
  salaryMax?: number;
  appliedDate?: string;
}

export interface ApplicationStats {
  total: number;
  active: number;
  inInterview: number;
  rejected: number;
  accepted: number;
}

export const STATUS_LABELS: Record<ApplicationStatus, string> = {
  [ApplicationStatus.RESEARCHING]: 'Researching',
  [ApplicationStatus.APPLIED]: 'Applied',
  [ApplicationStatus.PHONE_SCREEN]: 'Phone Screen',
  [ApplicationStatus.TECHNICAL_INTERVIEW]: 'Technical Interview',
  [ApplicationStatus.ONSITE_INTERVIEW]: 'Onsite Interview',
  [ApplicationStatus.OFFER]: 'Offer',
  [ApplicationStatus.REJECTED]: 'Rejected',
  [ApplicationStatus.WITHDRAWN]: 'Withdrawn',
  [ApplicationStatus.ACCEPTED]: 'Accepted',
};

export const STATUS_COLORS: Record<ApplicationStatus, string> = {
  [ApplicationStatus.RESEARCHING]: 'default',
  [ApplicationStatus.APPLIED]: 'primary',
  [ApplicationStatus.PHONE_SCREEN]: 'secondary',
  [ApplicationStatus.TECHNICAL_INTERVIEW]: 'warning',
  [ApplicationStatus.ONSITE_INTERVIEW]: 'info',
  [ApplicationStatus.OFFER]: 'success',
  [ApplicationStatus.REJECTED]: 'error',
  [ApplicationStatus.WITHDRAWN]: 'default',
  [ApplicationStatus.ACCEPTED]: 'success',
};