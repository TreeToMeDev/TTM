import { CSVFileStatus } from "../enums/csv-file-status.enum";

export interface FileUpload {
    fileCode: string;
    fileContentType: string;
    fileName: string;
    originalFileName: string;
    status: string;
}