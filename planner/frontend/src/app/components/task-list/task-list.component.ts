import { Component, OnInit } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { Router } from '@angular/router';
import { Category } from '../../models/category';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: any[] = [];
  totalPages: number = 0;
  currentPage: number = 0;
  selectedTask: any = null;
  optionsVisible: boolean = false;
  categories: Category[] = [];
  pages: number[] = [];
  params = {search: "", activeCategoryId: 0, currentPage: 0, pageSize: 6, sort: "title", direction: "asc"};
  searched = "";
  checked = "";

  constructor(private taskService: TaskService,
    private router: Router,
    private categoryService: CategoryService,
  ) {}

  ngOnInit(): void {
    this.loadTasks(this.params.currentPage);
    this.categoryService.getCategories().subscribe(res => this.categories = res);
  }

  loadTasks(page: number): void {
    this.params.currentPage = page;
    this.taskService.getTasks(this.params).subscribe(res => {
      this.tasks = res.content;
      this.totalPages = res.totalPages;
      this.currentPage = page + 1;
    });
  }

  filterBySearch() {
    this.params.search = this.searched;
    this.params.currentPage = 1;
    this.loadTasks(0);
  }

  filterByCategory(event: Event) {
    const categoryId = +(event.target as HTMLSelectElement).value;
    this.params.activeCategoryId = categoryId;
    this.params.currentPage = 1;
    this.loadTasks(0);
  }

  addTask(): void {
    this.router.navigateByUrl("tasks/new");
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.params.currentPage = this.currentPage;
      this.loadTasks(this.params.currentPage);
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.params.currentPage = this.currentPage - 1;
      this.loadTasks(this.params.currentPage);
    }
  }

  openModal(task: any): void {
    this.selectedTask = task;
    this.optionsVisible = false;
  }

  closeModal(): void {
    this.selectedTask = null;
    this.optionsVisible = false;
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe(res => {
      this.tasks = res;
      this.closeModal();
      this.loadTasks(0);
    });
  }

  editTask(id: number): void {
    this.router.navigateByUrl("tasks/edit/" + id);
  }

  toggleOptions(event: Event): void {
    event.stopPropagation();
    this.optionsVisible = !this.optionsVisible;
  }
}
