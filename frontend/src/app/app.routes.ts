import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login';
import { RegisterComponent } from './features/auth/register/register';
import { ForgotPasswordComponent } from './features/auth/forgot-password/forgot-password';
import { ResetPasswordComponent } from './features/auth/reset-password/reset-password';

import { DashboardComponent } from './features/dashboard/dashboard';

import { ProjectListComponent } from './features/projects/project-list/project-list';

import { TaskListComponent } from './features/tasks/task-list/task-list';
import { MyTasksComponent } from './features/tasks/my-tasks/my-tasks';

import { NotificationListComponent } from './features/notifications/notification-list/notification-list';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: 'forgot-password',
    component: ForgotPasswordComponent
  },

  {
    path: 'reset-password',
    component: ResetPasswordComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard]
  },

  {
    path: 'projects',
    component: ProjectListComponent,
    canActivate: [authGuard]
  },

  {
    path: 'tasks',
    component: TaskListComponent,
    canActivate: [authGuard]
  },

  {
    path: 'notifications',
    component: NotificationListComponent,
    canActivate: [authGuard]
  },

  {
    path: 'my-tasks',
    component: MyTasksComponent,
    canActivate: [authGuard]
  }

];