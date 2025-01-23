import { Routes } from '@angular/router';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TaskFormComponent } from './components/task-form/task-form.component';
import { CategoryComponent } from './components/category/category.component';

export const routes: Routes = [
    { path: "", redirectTo: 'tasks', pathMatch: 'full' },
    { path: "tasks", component: TaskListComponent },
    { path: "tasks/new", component: TaskFormComponent },
    { path: "tasks/edit/:id", component: TaskFormComponent },
    { path: "category", component: CategoryComponent },
    { path: "**", redirectTo: 'tasks' },
  ];
