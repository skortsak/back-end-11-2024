import { Category } from "./category";

export class Task {
    constructor(
        public title: string,
        public description: string,
        public category: Category,
        public completed: boolean,
        public createdAt: Date,
        public id?: number
    ) {}
}
