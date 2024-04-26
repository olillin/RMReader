# RMReader

Read and analyze the packet timestamps of `.tmcpr` [Replay Mod](https://www.replaymod.com/) Recordings.

See also [RMFixer](https://github.com/olillin/RMFixer).

## Output files

**RMReader** creates several files in the current directory as its output.
These are outlined below:

| filename         | description                                                                                  |
|------------------|----------------------------------------------------------------------------------------------|
| `timestamps`     | binary array of integers of all timestamps. Can be used as input instead of a `.tmcpr` file. |
| `timestamps.txt` | every timestamp in text, split by newlines.                                                  |
| `summary.txt`    | warns about large or negative gaps between timestamps along with line number.                |

## Usage


```bash
# Build (jar file is created in build/libs)
> ./gradlew build

# Run
> java -jar "RMReader-1.0-SNAPSHOT.jar" "<.tmcpr or timestamps file path>"
```
