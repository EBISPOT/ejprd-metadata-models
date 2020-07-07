package uk.ac.ebi.spot.ejprd.metadata.publisher;

import java.util.List;

public class FilesInDir {

    public static void main(String[] args){

        UtilityService utilityService = new UtilityService();

        List<String> filenames = utilityService.listAllFilesInADirectory("/Users/dipo/Documents/GitHub/EJP/EJPRD-metadata/EJPRD/ejprd_metadata_model/data/edit_json_files");

        int Count = filenames.size();

        System.out.println(filenames);
    }
}
//public class FilesInDir {
//
//
//    final File folder = new File("/home/you/Desktop");
//
//
//
//    public void FileInDir(final File folder) {
//        for (final File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                  ;
//            } else {
//                System.out.println(fileEntry.getName());
//            }
//        }
//    }




