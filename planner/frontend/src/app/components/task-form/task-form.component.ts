import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { TaskService } from '../../services/task.service';
import { Task } from '../../models/task';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent {
  taskForm!: FormGroup;
  categories: Category[] = [];
  isLoaded: boolean = false;
  date = new Date();
  formatedDate = formatDate(this.date, 'dd-MM-yyyy', 'en-EST');
  completed = false;

  constructor(private route: ActivatedRoute,
    private taskService: TaskService,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const taskId = this.route.snapshot.paramMap.get("id");
    this.categoryService.getCategories().subscribe(res => this.categories = res);

    if (taskId) {
      const taskIdNumber = parseInt(taskId);
      this.taskService.getTask(taskIdNumber).subscribe(res => {
        this.date = new Date(res.createdAt);
        this.formatedDate = formatDate(this.date, 'dd-MM-yyyy', 'en-EST');
        this.completed = res.completed;
        this.taskForm = new FormGroup({
          title: new FormControl(res.title),
          category: new FormControl(res.category.id),
          description: new FormControl(res.description),
          completed: new FormControl(res.completed)

        })
      })
    } else {
      this.taskForm = new FormGroup({
        title: new FormControl(''),
        category: new FormControl(''),
        description: new FormControl(''),
        completed: new FormControl(false)
      })
    }
    this.isLoaded = true;
  }

  onSubmit(): void {
    if (this.taskForm.valid) {
      const val = this.taskForm.value;
      const task = new Task(
        val.title,
        val.description,
        new Category(val.category),
        val.completed,
        val.date
      );

      const taskId = this.route.snapshot.paramMap.get('id');
      if (taskId) {
        const taskIdNumber = parseInt(taskId);
        task.id = taskIdNumber;
        this.taskService.updateTask(taskIdNumber, task).subscribe(() => {
          alert('Task updated successfully!');
          this.router.navigateByUrl('/tasks');
        });
      } else {
        this.taskService.createTask(task).subscribe(() => {
          alert('Task created successfully!');
          this.router.navigateByUrl('/tasks');
        });
      }
    }
  }
} 
