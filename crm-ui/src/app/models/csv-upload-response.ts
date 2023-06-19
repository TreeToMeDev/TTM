import { CSVFileDetails } from "./csv-file-details";
import { FileUpload } from "./file-upload";

export interface CSVUploadResponse {
    fileUpload: FileUpload;
    fileDetails: CSVFileDetails;
    errors: string[];
    postCount: number;
}