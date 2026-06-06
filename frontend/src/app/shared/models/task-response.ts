export interface TaskResponse {

  id: number;

  title: string;

  description: string;

  status: string;

  projectId: number;

  projectName: string;

  assignedUserId: number | null;

  assignedUsername: string | null;
}